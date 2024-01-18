package sopt.motivoo.util.custom_view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import sopt.motivoo.R
import sopt.motivoo.databinding.MotivooStepCountTextBinding

class MotivooStepCountText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private lateinit var binding: MotivooStepCountTextBinding

    init {
        initView()
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MotivooStepCountText, 0, 0
        ).apply {
            binding.tvStepCount.text = getString(R.styleable.MotivooStepCountText_myStepCountText)
            recycle()
        }
    }

    private fun initView() {
        binding = MotivooStepCountTextBinding.bind(
            inflate(
                context,
                R.layout.motivoo_step_count_text,
                this
            )
        )
    }

    fun setMyStepCountText(stepCountText: String) {
        binding.tvStepCount.text = stepCountText
    }

    fun setOtherStepCountText(otherStepCountText: String) {
        binding.tvOtherStepCount.text = otherStepCountText
    }
}
