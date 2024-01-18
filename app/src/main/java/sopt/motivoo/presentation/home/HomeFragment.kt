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
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.domain.entity.MotivooStorage
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


        if (homePermissionsGranted()) {
            startStepCountService()
        }

        Firebase.database.reference.child(USERS).child(pref.otherId.toString()).get()
            .addOnSuccessListener {
                if (it.value != null) {
                    Timber.tag("로그").e("my : ${pref.myStepCount} / oth : ${it.value}")
                    viewModel.patchHome(pref.myStepCount, it.value.toString().toInt())
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
            pref.userId = it.userId
            pref.otherId = it.opponentUserId
            pref.myGoalStepCount = it.userGoalStepCount
            pref.otherGoalStepCount = it.opponentUserGoalStepCount

            if (it.userType == getString(R.string.home_child)) {
                initRunnerIcon(
                    R.drawable.ic_child_user,
                    R.drawable.ic_parent_other
                )
                binding.motivooStepCountText.setOtherStepCountTitleText(getString(R.string.home_parent_step_count))
                binding.motivooStepCountTextUnselectedMission.setOtherStepCountTitleText(
                    getString(R.string.home_parent_step_count)
                )
            } else {
                initRunnerIcon(
                    R.drawable.ic_parent_user,
                    R.drawable.ic_child_other
                )
                binding.motivooStepCountText.setOtherStepCountTitleText(getString(R.string.home_child_step_count))
                binding.motivooStepCountTextUnselectedMission.setOtherStepCountTitleText(
                    getString(R.string.home_child_step_count)
                )
            }

            if (it.isStepCountCompleted) {
                binding.motivooMyPieChart.successStepCount()
                binding.motivooOtherPieChart.successStepCount()
            }


            // TODO: 파이어베이스 DB에 UID KEY 값으로 데이터가 들어가있지 않으면 걸음 수 넣어주기
            it.userId.let { userId ->
                Firebase.database.reference.child(USERS).child(userId.toString()).get()
                    .addOnSuccessListener {
                        if (it.value == null) {
                            Firebase.database.reference.child(USERS).child(userId.toString())
                                .setValue(pref.myStepCount)
                        }
                    }
            }

            // TODO: 파이어베이스 실시간으로 상대 걸음 수 가져오기
            Firebase.database.reference.child(USERS).child(it.opponentUserId.toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value != null) {
                            viewModel.setOtherStepCount(snapshot.value as Long)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
        }
        viewModel.otherStepCount.observe(viewLifecycleOwner) {
//            if (pref.otherStepCount >= pref.otherGoalStepCount) {
//                viewModel.patchHome(pref.myStepCount, pref.otherStepCount)
//            }
            binding.motivooStepCountText.setOtherStepCountText(it.toString())
            binding.motivooStepCountTextUnselectedMission.setOtherStepCountText(it.toString())
            if (pref.otherGoalStepCount != 0) {
                binding.motivooOtherPieChart.setStepCount(it / pref.otherGoalStepCount.toFloat())
            }
        }
        viewModel.stepCount.observe(viewLifecycleOwner) {
//            if (pref.myStepCount >= pref.myGoalStepCount - 7900) {
//                viewModel.patchHome(pref.myStepCount, pref.otherStepCount)
//            }
            binding.motivooStepCountText.setMyStepCountText(it.toString())
            binding.motivooStepCountTextUnselectedMission.setMyStepCountText(it.toString())
            Timber.tag("로그").e("stepCount : ${it}")
            Timber.tag("로그").e("my goal : ${pref.myGoalStepCount}")
            if (pref.otherGoalStepCount != 0) {
                binding.motivooMyPieChart.setStepCount(it / pref.myGoalStepCount.toFloat())
            }
        }
        viewModel.isSelectedMission.observe(viewLifecycleOwner) {
            viewModel.patchHome(pref.myStepCount, pref.otherStepCount)
            viewModel.postMissionTodayChoice()
        }
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
