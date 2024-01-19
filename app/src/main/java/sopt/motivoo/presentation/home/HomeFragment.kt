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
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.domain.entity.MotivooStorage
import sopt.motivoo.domain.entity.home.HomeData
import sopt.motivoo.domain.entity.home.MissionChoiceData
import sopt.motivoo.util.Constants.USERS
import sopt.motivoo.util.binding.BindingFragment
import sopt.motivoo.util.extension.setVisible
import timber.log.Timber
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
            startStepCountService()
            if (viewModel.missionChoiceData.value?.isChoiceFinished == true) {
                initMissionSelectedHasPermission()
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

        // TODO:: 테스트
//        pref.myStepCount = 0 // TODO:: 내 걸음 수 초기화
//        Firebase.database.reference.child(USERS).child(pref.userId.toString()).setValue(0) // TODO:: 내 걸음 수 파이어베이스 초기화
//        Firebase.database.reference.child(USERS).child(pref.otherId.toString()).setValue(0) // TODO:: 상대 걸음 수 초기화

        if (homePermissionsGranted()) {
            Timber.tag("로그").e("has perm in activity")
            startStepCountService()
            binding.motivooStepCountText.setMyStepCountText(pref.myStepCount.toString())
        }

        // TODO:: 홈 진입 시 상대 걸음 수 KEY 파이어베이스에 존재할 시, PATCH /home api
        Firebase.database.reference.child(USERS).child(pref.otherId.toString()).get()
            .addOnSuccessListener {
                if (it.value != null) {
                    if (pref.myGoalStepCount != 0) {
                        Timber.tag("로그").e("1 : ${pref.myStepCount} / 2: ${pref.myGoalStepCount} / 3: ${it.value} / 4: ${pref.otherGoalStepCount}")
                        if (pref.myStepCount >= pref.myGoalStepCount - MY_GOAL && (it.value as Long) >= pref.otherGoalStepCount - OTHER_GOAL) {
                            successMission()
                        }
                    }
                    viewModel.patchHome(
                        pref.myStepCount + MY_GOAL,
                        (it.value as Long).toInt() + OTHER_GOAL
                    )
                }
            }
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
            ).also {
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
            ).also {
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
            saveUserStepCountInfo(it)
            initUserIcon(it)
            separationUserType(it)

            if (it.isStepCountCompleted) binding.btnVerifyExercise.isEnabled = true

            // TODO: 파이어베이스 DB에 UID KEY 값으로 데이터가 들어가있지 않으면 걸음 수 넣어주기
            it.userId.let { userId ->
                viewModel.setMyStepCount(userId.toString())
            }

            // TODO: 파이어베이스 실시간으로 상대 걸음 수 가져오기
            viewModel.setOtherStepCount(it.opponentUserId.toString())
        }
        viewModel.otherStepCount.observe(viewLifecycleOwner) {
            viewModel.stepCount.value?.let { stepCount ->
                Timber.tag("로그").e("1 : $it / 2: ${pref.otherGoalStepCount} / 3: $stepCount / 4: ${pref.myGoalStepCount}")

                if (pref.myGoalStepCount != 0 && pref.otherStepCount != 0) {
                    if (it >= (pref.otherGoalStepCount - OTHER_GOAL) && stepCount >= (pref.myGoalStepCount - MY_GOAL)) {
                        successMission()
                    }
                }
            }
            binding.motivooStepCountText.setOtherStepCountText(it.toString())
            binding.motivooStepCountTextUnselectedMission.setOtherStepCountText(it.toString())
            if (pref.otherGoalStepCount != 0) binding.motivooOtherPieChart.setStepCount(it / (pref.otherGoalStepCount.toFloat() - OTHER_GOAL))
        }
        viewModel.stepCount.observe(viewLifecycleOwner) { stepCount ->
            viewModel.otherStepCount.value?.let {
                Timber.tag("로그").e("1 : $it / 2: ${pref.otherGoalStepCount} / 3: $stepCount / 4: ${pref.myGoalStepCount}")
                if (pref.myGoalStepCount != 0 && pref.otherStepCount != 0) {
                    if (it >= (pref.otherGoalStepCount - OTHER_GOAL) && stepCount >= (pref.myGoalStepCount - MY_GOAL)) {
                        successMission()
                    }
                }
            }
            if (stepCount == (pref.myGoalStepCount - MY_GOAL)) {
                Firebase.database.reference.child(USERS).child(pref.otherId.toString()).get()
                    .addOnSuccessListener {
                        if (it.value != null) {
                            viewModel.patchHome(
                                stepCount + MY_GOAL,
                                (it.value as Long).toInt() + OTHER_GOAL
                            )
                        }
                    }
            }
            binding.motivooStepCountText.setMyStepCountText(stepCount.toString())
            binding.motivooStepCountTextUnselectedMission.setMyStepCountText(stepCount.toString())
            if (pref.myGoalStepCount != 0) {
                binding.motivooMyPieChart.setStepCount(stepCount / (pref.myGoalStepCount.toFloat() - MY_GOAL))
            }
        }
        viewModel.isSelectedMission.observe(viewLifecycleOwner) {
            viewModel.patchHome(pref.myStepCount, pref.otherStepCount)
            viewModel.postMissionTodayChoice()
        }
    }

    private fun separationUserType(it: HomeData) {
        if (it.userType == getString(R.string.home_child)) {
            binding.motivooStepCountText.setOtherStepCountTitleText(getString(R.string.home_parent_step_count))
            binding.motivooStepCountTextUnselectedMission.setOtherStepCountTitleText(
                getString(R.string.home_parent_step_count)
            )
            if (viewModel.isCompletedStepCount.value == false) {
                binding.motivooMyPieChart.successStepCount(R.drawable.ic_child_user)
                binding.motivooOtherPieChart.successStepCount(R.drawable.ic_parent_other)
            }
        } else {
            binding.motivooStepCountText.setOtherStepCountTitleText(getString(R.string.home_child_step_count))
            binding.motivooStepCountTextUnselectedMission.setOtherStepCountTitleText(
                getString(R.string.home_child_step_count)
            )
            if (viewModel.isCompletedStepCount.value == false) {
                binding.motivooMyPieChart.successStepCount(R.drawable.ic_parent_user)
                binding.motivooOtherPieChart.successStepCount(R.drawable.ic_child_other)
            }
        }
    }

    private fun initUserIcon(it: HomeData) {
        if (!it.isStepCountCompleted) {
            if (it.userType == getString(R.string.home_child)) {
                initRunnerIcon(
                    R.drawable.ic_child_user,
                    R.drawable.ic_parent_other
                )
            } else {
                initRunnerIcon(
                    R.drawable.ic_parent_user,
                    R.drawable.ic_child_other
                )
            }
        }
    }

    private fun saveUserStepCountInfo(it: HomeData) {
        pref.userId = it.userId
        pref.otherId = it.opponentUserId
        pref.myGoalStepCount = it.userGoalStepCount
        pref.otherGoalStepCount = it.opponentUserGoalStepCount
    }

    private fun successMission() {
        viewModel.setCompletedStepCount(true)
        binding.motivooMyPieChart.successStepCount(null)
        binding.motivooOtherPieChart.successStepCount(null)
        TransitionManager.beginDelayedTransition(binding.root as? ViewGroup)
        binding.ivStepCount.setVisible(INVISIBLE)
        binding.ivMissionCompleted.setVisible(VISIBLE)
        binding.tvExercisePercent.text = "하이파이브! 성공!"
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
    }

    private fun initMissionSelectedNotPermission() {
        binding.motivooStepCountText.setVisible(GONE)
        binding.motivooDeniedPermissionSelected.setVisible(VISIBLE)
        binding.btnVerifyExercise.setVisible(GONE)
    }

    private fun initMissionSelectedHasPermission() {
        binding.motivooStepCountText.setVisible(VISIBLE)
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
        Firebase.database.reference.child(USERS).child(pref.otherId.toString()).get()
            .addOnSuccessListener {
                if (it.value != null) {
                    viewModel.patchHome(
                        pref.myStepCount + MY_GOAL,
                        (it.value as Long).toInt() + OTHER_GOAL
                    )
                }
            }
        if (homePermissionsGranted()) {
            initMissionSelectedHasPermission()
            removeBlurEffect()
            binding.motivooStepCountText.setMyStepCountText(pref.myStepCount.toString())
        }
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
                    viewModel.setStepCount(count)
                }
            }
        }
    }

    companion object {
        const val STEP_COUNT = "step_count"
        const val PACKAGE = "package"

        const val MY_GOAL = 0
        const val OTHER_GOAL = 0

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
