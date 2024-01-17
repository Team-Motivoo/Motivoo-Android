package sopt.motivoo.util.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import sopt.motivoo.R
import sopt.motivoo.databinding.MotivooMissionCardBinding

class MotivooMissionCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private lateinit var binding: MotivooMissionCardBinding

    init {
        initView()
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.MotivooMissionCard, 0, 0
        ).apply {
            recycle()
        }
    }

    private fun initView() {
        binding = MotivooMissionCardBinding.bind(
            inflate(
                context,
                R.layout.motivoo_mission_card,
                this
            )
        )
    }

    fun setMissionImage(missionImage: String) {
        binding.ivMission.load(missionImage)
    }

    fun setMissionText(missionText: String) {
        binding.tvMission.text = missionText
    }
}
