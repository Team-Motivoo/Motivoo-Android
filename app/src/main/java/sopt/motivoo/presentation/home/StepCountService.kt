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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.presentation.home.HomeFragment.Companion.STEP_COUNT
import sopt.motivoo.util.Constants.USERS
import sopt.motivoo.util.extension.sendNotification
import javax.inject.Inject

@AndroidEntryPoint
class StepCountService : Service() {
    private var job: Job? = null

    @Inject
    lateinit var pref: MotivooStorage

    @Inject
    lateinit var firebaseRealtimeDB: FirebaseDatabase

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorTypeStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        visibleStepCount()
        getStepCount(sensorManager, sensorTypeStepDetector)

        return START_NOT_STICKY
    }

    private fun getStepCount(
        sensorManager: SensorManager,
        sensorTypeStepDetector: Sensor?,
    ) {
        if (job?.isActive == null) {
            job = CoroutineScope(Dispatchers.Default).launch {
                object : SensorEventListener {
                    override fun onSensorChanged(sensorEvent: SensorEvent?) {
                        when (sensorEvent?.sensor?.type) {
                            Sensor.TYPE_STEP_DETECTOR -> {
                                if (pref.myStepCount < pref.myGoalStepCount) {
                                    pref.myStepCount += 1
                                    firebaseRealtimeDB.reference.child(USERS)
                                        .child(pref.userId.toString()).setValue(pref.myStepCount)
                                    visibleStepCount()
                                }
                            }
                        }
                    }

                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
                }.let {
                    sensorManager.registerListener(
                        it, sensorTypeStepDetector, SensorManager.SENSOR_DELAY_UI, 0
                    )
                }

                eventOtherStepCount()
            }
        }
    }

    private fun eventOtherStepCount() {
        firebaseRealtimeDB.reference.child(USERS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.child(pref.userId.toString()).apply {
                        if (exists()) {
                            (value as Long).let { if (it == 0L) pref.myStepCount = 0 }
                            visibleStepCount()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun visibleStepCount() {
        sendBroadcast(getStepCountIntent())
        initializeNotification(pref.myStepCount)
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

    private fun getStepCountIntent(): Intent = Intent().apply {
        action = STEP_COUNT
        putExtra(STEP_COUNT, pref.myStepCount)
        `package` = this@StepCountService.packageName
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    companion object {
        private const val FOREGROUND_SERVICE_ID = 1
    }
}
