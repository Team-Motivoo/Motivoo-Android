package sopt.motivoo.presentation.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible
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
            startStepCountService()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backPressed()
        if (homePermissionsGranted()) {
            startStepCountService()
        } else {
            requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
        }

        /**
         * 미션 선택 전 홈
         */
        binding.tvHomeToday.text = "2024년 1월 4일"
        binding.tvHomeTodayExerciseMission.text =
            getString(R.string.home_today_exercise_description)
        binding.motivooFirstMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걷고 스쿼트 10번 하기")
        }
        binding.motivooSecondMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걸어서 트리 보러가기")
        }

        binding.motivooStepCountTextUnselectedMission.setMyStepCountText("9000")
        binding.motivooStepCountTextUnselectedMission.setOtherStepCountText("9000")

        /**
         * 미션 선택 후 홈
         */
        binding.motivooFirstMissionCard.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)
            binding.cslUnselectedMission.setVisible(GONE)
            binding.cslSelectedMission.setVisible(VISIBLE)
            binding.tvHomeToday.text = getString(R.string.home_today_exercise)
            binding.tvHomeTodayExerciseMission.text = "8천걸음 걷고\n스탠딩 랫폴다운 20번 하기"
            binding.motivooStepCountText.setMyStepCountText("10000")
            binding.motivooStepCountText.setOtherStepCountText("9000")
            binding.tvExerciseMethod.setVisible(VISIBLE)
            binding.motivooMyPieChart.setStepCount(8 / 10f)
            binding.motivooOtherPieChart.setStepCount(6 / 10f)
            binding.btnVerifyExercise.isEnabled = true
        }

        navigateToHomeBottomSheetFragment()

        stepCountReceiver = StepCountReceiver()
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            })
    }

    private fun navigateToHomeBottomSheetFragment() {
        binding.btnVerifyExercise.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeBottomSheetFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        ContextCompat.registerReceiver(
            requireContext(), stepCountReceiver, getIntentFilter(),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
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

    private fun getIntentFilter() = IntentFilter().apply {
        addAction(STEP_COUNT)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(stepCountReceiver)
    }

    inner class StepCountReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == STEP_COUNT) {
                // TODO:: 걸음 수 렌더링
//                binding.motivooStepCountText.setStepCountText(
//                    intent.getIntExtra(STEP_COUNT, 0).toString()
//                )
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
