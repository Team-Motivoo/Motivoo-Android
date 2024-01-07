package sopt.motivoo.presentation

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import sopt.motivoo.R
import sopt.motivoo.databinding.FragmentHomeBinding
import sopt.motivoo.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.motivooStepCountText.setStepCountText("0")

        /**
         * XXX 삭제 XXX
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
         * XXX 삭제 XXX
         * 원형 프로그래스 바 동작하는 거 보려고 SeekBar 만들어 놓았습니다.
         */
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.motivooStepCountText.setStepCountText(p1.toString())
                binding.motivooPieChart.setStepCount(((p1 / 100.0) * 320).toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }
}
