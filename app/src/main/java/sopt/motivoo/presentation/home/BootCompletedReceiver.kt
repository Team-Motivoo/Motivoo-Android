package sopt.motivoo.presentation.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.domain.entity.MotivooStorage
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var pref: MotivooStorage

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val intentStepCountService = Intent(context, StepCountService::class.java)

        if (action == Intent.ACTION_BOOT_COMPLETED) {
            context?.startService(intentStepCountService)
        }
    }
}
