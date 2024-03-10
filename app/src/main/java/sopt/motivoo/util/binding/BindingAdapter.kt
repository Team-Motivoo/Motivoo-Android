package sopt.motivoo.util.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.android.motivoo_design.MotivooOtherPieChart
import com.android.motivoo_design.MotivooPieChart

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
            return
        }
        if (stepCountGoal == 0) {
            setPercent(0f)
            return
        }
        setPercent(stepCount / stepCountGoal.toFloat())
    }
}
