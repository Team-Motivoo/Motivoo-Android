package sopt.motivoo.presentation.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.RenderEffect
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.TransitionManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var stepCountReceiver: StepCountReceiver

    private val viewModel: HomeViewModel by activityViewModels()

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
            Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_SHORT).show()
        } else {
            if (viewModel.missionChoiceData.value?.isChoiceFinished == true) {
                initMissionSelectedHasPermission()
                startStepCountService()
                removeBlurEffect()
            } else {
                initMissionUnSelectedHasPermission()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        stepCountReceiver = StepCountReceiver()

        backPressed()
        if (homePermissionsGranted()) {
            viewModel.setStepCount(pref.myStepCount)
            viewModel.setOtherStepCount(pref.otherStepCount)
            startStepCountService()
        }

        viewModel.patchHome(pref.myStepCount, pref.otherStepCount)
        viewModel.postMissionTodayChoice()

        binding.motivooFirstMissionCard.setOnClickListener {
            choiceMission(0)
        }

        binding.motivooSecondMissionCard.setOnClickListener {
            choiceMission(1)
        }

        binding.tvExerciseMethod.setOnClickListener {
            navigateToExerciseMethodNotion()
        }

        binding.motivooDeniedPermissionSelected.setOnClickListener {
            intentAppSettings()
        }

        binding.motivooDeniedPermissionUnselected.setOnClickListener {
            intentAppSettings()
        }

        navigateToHomeBottomSheetFragment()
        observeData()
    }

    private fun choiceMission(missionPosition: Int) {
        viewModel.missionChoiceData.value?.missionChoiceList?.get(missionPosition)?.let {
            viewModel.postMissionToday(it.missionId)
        }
    }

    private fun navigateToExerciseMethodNotion() {
        val action = HomeFragmentDirections.actionHomeFragmentToWebViewFragment(
            viewModel.missionChoiceData.value?.todayMission?.missionDescription
                ?: return
        )
        findNavController().navigate(action)
    }

    private fun intentAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts(PACKAGE, context?.packageName, null)
        }
        startActivity(intent)
    }

    private fun updateBlurEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect.createBlurEffect(
                10f, 10f, Shader.TileMode.CLAMP
            ).also { it ->
                binding.motivooMyPieChart.setRenderEffect(it)
                binding.motivooOtherPieChart.setRenderEffect(it)
                binding.ivMissionCompleted.setRenderEffect(it)
                binding.ivStepCount.setRenderEffect(it)
                binding.tvExercisePercent.setRenderEffect(it)
            }
        }
    }

    private fun removeBlurEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect.createBlurEffect(
                10f, 10f, Shader.TileMode.CLAMP
            ).also { it ->
                binding.motivooMyPieChart.setRenderEffect(null)
                binding.motivooOtherPieChart.setRenderEffect(null)
                binding.ivMissionCompleted.setRenderEffect(null)
                binding.ivStepCount.setRenderEffect(null)
                binding.tvExercisePercent.setRenderEffect(null)
            }
        }
    }

    private fun observeData() {
        viewModel.missionChoiceData.observe(viewLifecycleOwner) {
            if (it.isChoiceFinished) {
                TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)
                if (homePermissionsGranted()) {
                    initMissionSelectedHasPermission()
                } else {
                    requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
                    initMissionSelectedNotPermission()
                    updateBlurEffect()
                }
            } else {
                setMissionCard(it)
                if (homePermissionsGranted()) {
                    initMissionUnSelectedHasPermission()
                } else {
                    requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
                    initMissionUnSelectedNotPermission()
                }
            }
        }
        viewModel.homeData.observe(viewLifecycleOwner) {
            when (it.userType) {
                getString(R.string.home_child) -> initRunnerIcon(
                    R.drawable.ic_child_user,
                    R.drawable.ic_parent_other
                )

                getString(R.string.home_parent) -> initRunnerIcon(
                    R.drawable.ic_parent_user,
                    R.drawable.ic_child_other
                )
            }
            pref.userId = it.userId
            pref.otherId = it.opponentUserId
            pref.myGoalStepCount = it.userGoalStepCount
            pref.otherGoalStepCount = it.opponentUserGoalStepCount
            viewModel.getEventOtherStepCount(it.opponentUserId)
        }
        viewModel.otherStepCount.observe(viewLifecycleOwner) { otherStepCount ->
            eventOtherStepCount(otherStepCount)
        }
        viewModel.stepCount.observe(viewLifecycleOwner) {
            binding.motivooStepCountText.setMyStepCountText(it.toString())
        }
    }

    private fun eventOtherStepCount(otherStepCount: Long) {
        binding.btnVerifyExercise.isEnabled = isVerifyExerciseButtonEnabled()
        binding.motivooStepCountText.setOtherStepCountText(otherStepCount.toString())
        binding.motivooOtherPieChart.setStepCount((otherStepCount.toFloat() / pref.otherGoalStepCount))
        binding.motivooStepCountTextUnselectedMission.setOtherStepCountText(otherStepCount.toString())
    }

    private fun initRunnerIcon(myIcon: Int, otherIcon: Int) {
        binding.motivooMyPieChart.setMyIcon(myIcon)
        binding.motivooOtherPieChart.setOtherIcon(otherIcon)
    }

    private fun initMissionUnSelectedNotPermission() {
        binding.motivooStepCountTextUnselectedMission.setVisible(GONE)
        binding.motivooDeniedPermissionUnselected.setVisible(VISIBLE)
    }

    private fun initMissionUnSelectedHasPermission() {
        binding.motivooStepCountTextUnselectedMission.setVisible(VISIBLE)
        binding.motivooDeniedPermissionUnselected.setVisible(GONE)
        binding.motivooStepCountText.setOtherStepCountText(pref.otherStepCount.toString())
    }

    private fun initMissionSelectedNotPermission() {
        binding.motivooStepCountText.setVisible(GONE)
        binding.motivooDeniedPermissionSelected.setVisible(VISIBLE)
        binding.btnVerifyExercise.setVisible(GONE)
    }

    private fun initMissionSelectedHasPermission() {
        binding.motivooStepCountText.setVisible(VISIBLE)
        binding.motivooMyPieChart.setStepCount(pref.myStepCount / pref.myGoalStepCount.toFloat())
        binding.motivooDeniedPermissionSelected.setVisible(GONE)
        binding.btnVerifyExercise.setVisible(VISIBLE)
    }

    private fun setMissionCard(it: MissionChoiceData) {
        binding.motivooFirstMissionCard.apply {
            setMissionImage(it.missionChoiceList[0].missionIconUrl)
            setMissionText(it.missionChoiceList[0].missionContent)
        }
        binding.motivooSecondMissionCard.apply {
            setMissionImage(it.missionChoiceList[1].missionIconUrl)
            setMissionText(it.missionChoiceList[1].missionContent)
        }
    }

    private fun backPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
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
                intent.getIntExtra(STEP_COUNT, 0).let { count ->
                    eventMyStepCount(count)
                }
            }
        }

        private fun eventMyStepCount(count: Int) {
            viewModel.setMyStepCount(pref.userId, count)
            binding.motivooStepCountText.setMyStepCountText(count.toString())
            binding.motivooMyPieChart.setStepCount(count.toFloat() / pref.myGoalStepCount)
            binding.motivooStepCountTextUnselectedMission.setMyStepCountText(count.toString())
            binding.btnVerifyExercise.isEnabled = isVerifyExerciseButtonEnabled()
        }
    }

    private fun isVerifyExerciseButtonEnabled(): Boolean =
        pref.otherStepCount == pref.otherGoalStepCount && pref.myStepCount == pref.myGoalStepCount

    companion object {
        const val STEP_COUNT = "step_count"
        const val PACKAGE = "package"

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
