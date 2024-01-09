package sopt.motivoo.presentation.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.presentation.home.HomeFragment.Companion.STEP_COUNT
import sopt.motivoo.util.sendNotification
import javax.inject.Inject

@AndroidEntryPoint
class StepCountService : Service() {
    private var job: Job? = null
    private lateinit var stepCountListener: SensorEventListener

    @Inject
    lateinit var pref: MotivooStorage

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorTypeStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        visibleStepCount()

        if (pref.stepCountServiceFlag != 1) {
            job = CoroutineScope(Dispatchers.Default).launch {
                stepCountListener = object : SensorEventListener {
                    override fun onSensorChanged(sensorEvent: SensorEvent?) {
                        when (sensorEvent?.sensor?.type) {
                            Sensor.TYPE_STEP_DETECTOR -> {
                                pref.stepCount += 1
                                visibleStepCount()
                            }
                        }
                    }

                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
                }

                sensorManager.registerListener(
                    stepCountListener, sensorTypeStepDetector, SensorManager.SENSOR_DELAY_UI, 0
                )
            }
        }

        return START_NOT_STICKY
    }

    private fun visibleStepCount() {
        sendBroadcast(getStepCountIntent())
        initializeNotification(pref.stepCount)
    }

    private fun initializeNotification(stepCount: Int) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(manager)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            startForeground(
                FOREGROUND_SERVICE_ID,
                manager.sendNotification(
                    getString(R.string.notification_text, stepCount),
                    applicationContext
                ),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(
                FOREGROUND_SERVICE_ID,
                manager.sendNotification(
                    getString(R.string.notification_text, stepCount),
                    applicationContext
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

    private fun getStepCountIntent(): Intent = Intent().apply {
        action = STEP_COUNT
        putExtra(STEP_COUNT, pref.stepCount)
        `package` = this@StepCountService.packageName
    }

    override fun onDestroy() {
        job?.cancel()
        stopSelf()
        super.onDestroy()
    }

    companion object {
        private const val FOREGROUND_SERVICE_ID = 1
    }
}
