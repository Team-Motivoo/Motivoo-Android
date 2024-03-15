package sopt.motivoo.presentation.home.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sopt.motivoo.di.DefaultDispatcher
import sopt.motivoo.domain.repository.UserRepository
import sopt.motivoo.presentation.home.service.StepCountService
import sopt.motivoo.util.Constants.USER_ID
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {
    @Inject
    lateinit var userRepository: UserRepository

    @DefaultDispatcher
    @Inject
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val intentStepCountService = Intent(context, StepCountService::class.java)

        when (action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                CoroutineScope(coroutineDispatcher).launch {
                    val userId = userRepository.getUserId()
                    if (userId != -1) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intentStepCountService.putExtra(USER_ID, userId)
                            context?.startForegroundService(intentStepCountService)
                        } else {
                            intentStepCountService.putExtra(USER_ID, userId)
                            context?.startService(intentStepCountService)
                        }
                    }
                }
            }
        }
    }
}
