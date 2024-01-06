package sopt.motivoo.util.binding

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("selected")
    fun View.selected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}
