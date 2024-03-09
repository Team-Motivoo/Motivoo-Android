package sopt.motivoo.presentation.home.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sopt.motivoo.data.repository.HomeRepositoryImpl
import sopt.motivoo.data.repository.StepCountRepositoryImpl
import sopt.motivoo.di.DefaultDispatcher
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.repository.StepCountRepository
import sopt.motivoo.domain.repository.UserRepository
import sopt.motivoo.presentation.home.service.StepCountService
import sopt.motivoo.util.Constants.USER_ID
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver  : BroadcastReceiver() {
    @Inject
    lateinit var userRepository: UserRepository

    @DefaultDispatcher
    @Inject
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val intentStepCountService = Intent(context, StepCountService::class.java)

        if (action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(coroutineDispatcher).launch {
                val userId = userRepository.getUserId()
                if (userId != -1) {
                    intentStepCountService.putExtra(USER_ID, userId)
                    context?.startService(intentStepCountService)
                }
            }
        }
    }
}
