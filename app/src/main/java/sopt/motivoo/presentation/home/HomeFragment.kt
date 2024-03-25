package sopt.motivoo.presentation.home

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.utils.Child
import com.android.utils.Parent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.presentation.home.service.StepCountService
import sopt.motivoo.presentation.home.viewmodel.HomeViewModel
import sopt.motivoo.util.Constants.USER_ID
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.removeBlurEffect
import sopt.motivoo.util.extension.showSnackbar
import sopt.motivoo.util.extension.updateBlurEffect
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by activityViewModels()
    val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    private val requestHomePermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var permissionGranted = true
        var educationGranted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Timber.e("aaa can do it : ${alarmManager.canScheduleExactAlarms()} ")
        }
        permissions.entries.forEach {
            Timber.e("aaa it.key : ${it.key} / it.value : ${it.value}")
            if (it.key in Manifest.permission.POST_NOTIFICATIONS && it.value == false) {
                permissionGranted = false
            }
            if (it.key in Manifest.permission.ACTIVITY_RECOGNITION && it.value == false) {
                permissionGranted = false
            }

            if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) && it.value == false) {
                educationGranted = true
            }
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACTIVITY_RECOGNITION) && it.value == false) {
                educationGranted = true
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    permissionGranted = false
                    educationGranted = false
                } else {
                    permissionGranted = true
                    educationGranted = true
                }
            }
        }

        if (!permissionGranted) {
            if (educationGranted && viewModel.isMissionChoiceFinished.value == true) intentAppSettings() // TODO :: 교육용 팝업
            else permissionDenied()
        } else {
            permissionGranted()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        backPressed()

        initHomePermissionsState()
        viewModel.postMissionTodayChoice()

        onClickPermission()
        onClickMission()
        onClickExerciseMethod()
        onClickVerifyButton()

        collectHomeState()
        handleBackNavStack()
    }

    private fun collectHomeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect { homeState ->
                when (homeState) {
                    is HomeState.Idle -> {
                        Timber.e("Idle")
                    }

                    is HomeState.Loading -> {
                        binding.pvLoading.visibility = View.VISIBLE
                    }

                    is HomeState.FetchHomeData -> {
                        binding.pvLoading.visibility = View.GONE

                        viewModel.stepCountGoal.value = homeState.homeData.userGoalStepCount
                        viewModel.otherStepCountGoal.value =
                            homeState.homeData.opponentUserGoalStepCount
                        viewModel.isCompletedMission.value =
                            homeState.homeData.isMissionImageCompleted

                        viewModel.userType.value = when (homeState.homeData.userType) {
                            requireContext().getString(R.string.home_child) -> Child
                            requireContext().getString(R.string.home_parent) -> Parent
                            else -> null
                        }
                        viewModel.anotherUserType.value = when (homeState.homeData.userType) {
                            requireContext().getString(R.string.home_child) -> Parent
                            requireContext().getString(R.string.home_parent) -> Child
                            else -> null
                        }

                        checkPermissionIfUnSelectedMission()
                        checkHomeAlarmPermission(homeState)
                    }

                    is HomeState.SelectedMissionData -> {
                        TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)

                        binding.tvSelectedMissionHomeToday.text =
                            getString(R.string.home_today_exercise)
                        binding.tvSelectedMissionHomeTodayExerciseMission.text =
                            homeState.todayMission.missionContent
                    }

                    is HomeState.UnSelectedMissionData -> {
                        TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)

                        binding.tvUnselectedMissionHomeToday.text = homeState.date
                        binding.tvUnselectedMissionHomeTodayExerciseMission.text =
                            getString(R.string.home_today_exercise_description)
                        binding.motivooFirstMissionCard.setMissionText(homeState.missionChoiceList[0].missionContent)
                        binding.motivooFirstMissionCard.setMissionImage(homeState.missionChoiceList[0].missionIconUrl)
                        binding.motivooSecondMissionCard.setMissionText(homeState.missionChoiceList[1].missionContent)
                        binding.motivooSecondMissionCard.setMissionImage(homeState.missionChoiceList[1].missionIconUrl)
                    }

                    is HomeState.CompletedStepCount -> {
                        viewModel.isCompletedStepCount.value = true
                    }

                    is HomeState.CompletedMission -> {
                        TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)
                        viewModel.isCompletedStepCount.value = false
                    }

                    is HomeState.HighFive -> {
                        if (viewModel.isCompletedStepCount.value == true) viewModel.isCompletedStepCount.value =
                            false
                        viewModel.isHighFive.value = true
                    }

                    is HomeState.FailMatching -> {
                        findNavController().navigate(R.id.action_homeFragment_to_withdrawalFragment)
                    }

                    is HomeState.Confirm -> {
                        viewModel.postMissionTodayChoice()
                    }
                }
            }
        }
    }

    private fun checkPermissionIfUnSelectedMission() {
        if (viewModel.isMissionChoiceFinished.value != true) {
            requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
        }
    }

    private fun checkHomeAlarmPermission(homeState: HomeState.FetchHomeData) {
        if (checkPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    requireContext().showSnackbar(
                        binding.root,
                        "자정마다 걸음 수를 초기화하려면 알림 및 리마인더를 허용해주세요.",
                        "설정으로 이동",
                        true
                    ) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        startActivity(intent)
                    }
                } else {
                    startStepCountService(homeState.homeData.userId.toInt())
                }
            } else {
                startStepCountService(homeState.homeData.userId.toInt())
            }
        }
    }

    private fun handleBackNavStack() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(
            HOME_STATE_CONFIRM
        )?.observe(viewLifecycleOwner) {
            if (it == HOME_STATE_CONFIRM_RESULT_OK) viewModel.setHomeState(HomeState.Confirm)
        }
    }

    private fun onClickVerifyButton() {
        binding.btnVerifyExercise.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_homeBottomSheetFragment)
        }
    }

    private fun onClickExerciseMethod() {
        binding.tvExerciseMethod.setOnClickListener {
            navigateToExerciseMethodNotion()
        }
    }

    private fun onClickPermission() {
        binding.motivooSelectedMissionDeniedPermission.setOnClickListener {
            requestHomePermissionRequest.launch(HOME_REQUIRED_PERMISSIONS)
        }
    }

    private fun onClickMission() {
        binding.motivooFirstMissionCard.setOnClickListener {
            viewModel.handleHomeIntent(HomeIntent.FirstSelectMission)
        }

        binding.motivooSecondMissionCard.setOnClickListener {
            viewModel.handleHomeIntent(HomeIntent.SecondSelectMission)
        }
    }

    private fun initHomePermissionsState() {
        checkPermission().also {
            if (it) permissionGranted()
            else permissionDenied()
        }
    }

    private fun checkPermission(): Boolean = HOME_REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun permissionDenied() {
        updateBlurEffect()
        viewModel.isPermissionGranted.value = false
    }

    private fun permissionGranted() {
        removeBlurEffect()
        viewModel.isPermissionGranted.value = true
    }

    private fun updateBlurEffect() {
        binding.motivooMyPieChart.updateBlurEffect()
        binding.motivooOtherPieChart.updateBlurEffect()
        binding.ivMissionCompleted.updateBlurEffect()
        binding.ivStepCount.updateBlurEffect()
    }

    private fun removeBlurEffect() {
        binding.motivooMyPieChart.removeBlurEffect()
        binding.motivooOtherPieChart.removeBlurEffect()
        binding.ivMissionCompleted.removeBlurEffect()
        binding.ivStepCount.removeBlurEffect()
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

    private fun startStepCountService(userId: Int) {
        requireContext().apply {
            val intent = Intent(this, StepCountService::class.java).apply {
                putExtra(USER_ID, userId)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    companion object {
        const val PACKAGE = "package"
        const val HOME_STATE_CONFIRM = "home_state_confirm"
        const val HOME_STATE_CONFIRM_RESULT_OK = 1

        private val HOME_REQUIRED_PERMISSIONS =
            mutableListOf<String>().apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    add(Manifest.permission.ACTIVITY_RECOGNITION)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    add(Manifest.permission.POST_NOTIFICATIONS)
                    add(Manifest.permission.SCHEDULE_EXACT_ALARM)
                }
            }.toTypedArray()
    }
}
