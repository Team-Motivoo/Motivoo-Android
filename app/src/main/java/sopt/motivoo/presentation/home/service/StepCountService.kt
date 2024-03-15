package sopt.motivoo.presentation.home.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.domain.repository.FirebaseRepository
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.presentation.home.broadcastreceiver.HomeAlarmReceiver
import sopt.motivoo.presentation.home.broadcastreceiver.HomeAlarmReceiver.Companion.ALARM_INIT_OK
import sopt.motivoo.util.Constants.USER_ID
import sopt.motivoo.util.extension.sendNotification
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class StepCountService : LifecycleService() {
    private var job: Job? = null
    private var isInitValue: Boolean = false
    private var userId: Int? = null
    private var sensorEventListener: SensorEventListener? = null
    private var sensorManager: SensorManager? = null
    private var alarmManager: AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent

    @Inject
    lateinit var stepCountRepository: StepCountRepository

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    override fun onCreate() {
        super.onCreate()
        isInitValue = true
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        setAlarm()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        lifecycleScope.launch {
            if (isInitValue) {
                userId = intent?.getIntExtra(USER_ID, 0)
                initializeNotification(stepCountRepository.getMyStepCount())
                isInitValue = false
            }
        }

        when (intent?.action) {
            STEP_COUNT_ACTION -> lifecycleScope.launch {
                stepCountRepository.addMyStepCount { count ->
                    initializeNotification(count)
                    userId?.let {
                        firebaseRepository.setUserStepCount(it.toLong(), count)
                    }
                }
            }
            STEP_COUNT_INIT_ACTION -> lifecycleScope.launch {
                val stepCount = stepCountRepository.getMyStepCount()
                if (stepCount != -1) {
                    initializeNotification(stepCount)
                }
            }
        }

        if (job?.isActive == null) {
            job = CoroutineScope(Dispatchers.Default).launch {
                sensorEventListener = object : SensorEventListener {
                    override fun onSensorChanged(sensorEvent: SensorEvent?) {
                        when (sensorEvent?.sensor?.type) {
                            Sensor.TYPE_STEP_DETECTOR -> startService(
                                Intent(
                                    baseContext,
                                    StepCountService::class.java
                                ).apply { action = STEP_COUNT_ACTION }
                            )
                        }
                    }

                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
                }.also {
                    sensorManager?.registerListener(
                        it,
                        sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                        SensorManager.SENSOR_DELAY_UI,
                        0
                    )
                }
            }
        }

        return START_NOT_STICKY
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, HomeAlarmReceiver::class.java).apply {
            action = ALARM_INIT_OK
        }
        pendingIntent = PendingIntent.getBroadcast(this, 0 ,intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun initializeNotification(stepCount: Int) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(manager)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            startForeground(
                FOREGROUND_SERVICE_ID,
                sendNotification(
                    getString(R.string.notification_title, stepCount),
                    getString(R.string.notification_content)
                ),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(
                FOREGROUND_SERVICE_ID,
                sendNotification(
                    getString(R.string.notification_title, stepCount),
                    getString(R.string.notification_content)
                )
            )
        }
    }

    private fun createNotificationChannel(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    getString(R.string.step_count_notification_channel_id),
                    getString(R.string.step_count_notification_channel_name),
                    NotificationManager.IMPORTANCE_NONE
                )
            )
        }
    }

    override fun onDestroy() {
        sensorManager?.unregisterListener(sensorEventListener)
        alarmManager?.cancel(pendingIntent)
        job?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val STEP_COUNT_ACTION = "STEP_COUNT_ACTION"
        const val STEP_COUNT_INIT_ACTION = "STEP_COUNT_INIT_ACTION"

        private const val FOREGROUND_SERVICE_ID = 1
    }
}
