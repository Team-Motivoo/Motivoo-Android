package sopt.motivoo.presentation.home.broadcastreceiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import sopt.motivoo.di.IoDispatcher
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.presentation.home.service.StepCountService
import sopt.motivoo.presentation.home.service.StepCountService.Companion.STEP_COUNT_INIT_ACTION
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class HomeAlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var stepCountRepository: StepCountRepository

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    private var job: Job? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ALARM_INIT_OK -> {
                job = CoroutineScope(ioDispatcher).launch {
                    try {
                        stepCountRepository.setMyStepCount(0)
                    } finally {
                        val intent = Intent(context, StepCountService::class.java).apply {
                            action = STEP_COUNT_INIT_ACTION
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context?.startForegroundService(intent)
                        } else {
                            context?.startService(intent)
                        }
                        this.cancel()
                    }
                }
                setAlarm(context)
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intentAlarm = Intent(context, HomeAlarmReceiver::class.java).apply {
            action = ALARM_INIT_OK
        }

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intentAlarm, PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    companion object {
        const val ALARM_INIT_OK = "alarm_init_ok"
    }
}
