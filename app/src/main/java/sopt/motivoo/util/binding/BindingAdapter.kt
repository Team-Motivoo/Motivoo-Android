package sopt.motivoo.util.binding

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.android.motivoo_design.MotivooOtherPieChart
import com.android.motivoo_design.MotivooPieChart
import sopt.motivoo.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("selected")
    fun View.selected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("stepCount", "stepCountGoal", "iconEnabled")
    fun MotivooPieChart.setPercent(stepCount: Int, stepCountGoal: Int, iconEnabled: Boolean) {
        if (iconEnabled) {
            this.iconEnabled = true
            this.degree = 120.0
            return
        }
        if (stepCountGoal == 0) {
            setPercent(0f)
            return
        }
        setPercent(stepCount / stepCountGoal.toFloat())
    }

    @JvmStatic
    @BindingAdapter("stepCount", "stepCountGoal", "iconEnabled")
    fun MotivooOtherPieChart.setPercent(stepCount: Int, stepCountGoal: Int, iconEnabled: Boolean) {
        if (iconEnabled) {
            this.iconEnabled = true
            this.degree = 120.0
            this.otherProgressBarPaint.color = ContextCompat.getColor(context, R.color.red_400_FF6259)
            return
        }
        if (stepCountGoal == 0) {
            setPercent(0f)
            return
        }
        setPercent(stepCount / stepCountGoal.toFloat())
    }
}
