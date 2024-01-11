package sopt.motivoo.presentation.home

import android.os.Bundle
import android.view.View
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeMissionBinding
import sopt.motivoo.util.binding.BindingFragment

class HomeMissionFragment :
    BindingFragment<FragmentHomeMissionBinding>(R.layout.fragment_home_mission) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.motivooFirstMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걷고 스쿼트 10번 하기")
        }
        binding.motivooSecondMissionCard.apply {
            setMissionImage(R.drawable.ic_clap_sound)
            setMissionText("8천걸음 걸어서 트리 보러가기")
        }

        binding.motivooStepCountText.setStepCountText("9000")
        binding.motivooStepCountText.setOtherStepCountText("9000")
    }
}
