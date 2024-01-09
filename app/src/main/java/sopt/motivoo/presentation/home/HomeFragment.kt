package sopt.motivoo.presentation.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.binding.BindingFragment
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var stepCountReceiver: StepCountReceiver

    @Inject
    lateinit var pref: MotivooStorage

    private val requestHomePermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in HOME_REQUIRED_PERMISSIONS && it.value == false) {
                permissionGranted = false
            }
        }
        if (!permissionGranted) {
            // permission denied
            Toast.makeText(requireContext(), "권한 허용 필요", Toast.LENGTH_SHORT).show()
        } else {
            // permission granted
            registerStepCountReceiver()
            startStepCountService()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        when (pref.stepCountServiceFlag) {
            -1 -> pref.stepCountServiceFlag = 0
            0 -> pref.stepCountServiceFlag = 1
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stepCountReceiver = StepCountReceiver()

        if (homePermissionsGranted()) {
            registerStepCountReceiver()
            startStepCountService()
        } else {
            requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
        }

        /**
         * 삭제될 것
         * 제공되는 미션 더미 처리
         */
        binding.motivooFirstMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걷고 스쿼트 10번 하기")
        }
        binding.motivooSecondMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걸어서 트리 보러가기")
        }
        /**
         * 삭제될 것
         * 파이 차트 원형 프로그래스 바
         */
        binding.motivooPieChart.setStepCount(((pref.stepCount / 100.0) * 320).toFloat())
    }

    override fun onResume() {
        super.onResume()
    }

    private fun homePermissionsGranted() = HOME_REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startStepCountService() {
        requireContext().apply {
            startService(Intent(this, StepCountService::class.java))
        }
    }

    private fun registerStepCountReceiver() {
        ContextCompat.registerReceiver(
            requireContext(), stepCountReceiver, addIntentFilter(),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    private fun addIntentFilter() = IntentFilter().apply {
        addAction(STEP_COUNT)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(stepCountReceiver)
    }

    inner class StepCountReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == STEP_COUNT) {
                binding.motivooStepCountText.setStepCountText(
                    intent.getIntExtra(STEP_COUNT, 0).toString() ?: pref.stepCount.toString()
                )
                /**
                 * 삭제될 것
                 * 파이 차트 원형 프로그래스 바
                 */
                binding.motivooPieChart.setStepCount(((pref.stepCount / 100.0) * 320).toFloat())
            }
        }
    }

    companion object {
        const val STEP_COUNT = "step_count"

        private val HOME_REQUIRED_PERMISSIONS =
            mutableListOf<String>().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    add(Manifest.permission.ACTIVITY_RECOGNITION)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    add(Manifest.permission.POST_NOTIFICATIONS)
                }
            }.toTypedArray()
    }
}
