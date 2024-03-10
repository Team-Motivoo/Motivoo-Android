package com.android.motivoo_design

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.motivoo_design.databinding.MotivooStepCountTextBinding
import com.android.utils.Child
import com.android.utils.MotivooUserType
import com.android.utils.Parent
import com.android.utils.toMotivooUserType

class MotivooStepCountText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private lateinit var binding: MotivooStepCountTextBinding

    var userType: MotivooUserType? = null
        set(value) {
            when (value) {
                Child -> {
                    binding.tvStepCountTitle.text =
                        context.getString(R.string.home_my_step_count)
                    binding.tvOtherStepCountTitle.text =
                        context.getString(R.string.home_parent_step_count)
                }

                Parent -> {
                    binding.tvStepCountTitle.text =
                        context.getString(R.string.home_my_step_count)
                    binding.tvOtherStepCountTitle.text =
                        context.getString(R.string.home_child_step_count)
                }

                else -> Unit
            }
            field = value
        }

    init {
        initView()
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MotivooStepCountText, 0, 0
        ).apply {
            binding.tvStepCount.text = getString(R.styleable.MotivooStepCountText_myStepCountText)
            binding.tvOtherStepCount.text =
                getString(R.styleable.MotivooStepCountText_otherStepCountText)
            userType = getInt(R.styleable.MotivooStepCountText_userType, 0).toMotivooUserType
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