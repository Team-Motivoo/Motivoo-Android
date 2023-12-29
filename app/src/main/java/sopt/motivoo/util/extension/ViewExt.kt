package sopt.motivoo.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, message: String, isShort: Boolean = true) {
    val duration = if (isShort) {
        Snackbar.LENGTH_SHORT
    } else {
        Snackbar.LENGTH_LONG
    }
    Snackbar.make(view, message, duration).show()
}

fun View.setVisible(visibility: Int) {
    this.visibility = visibility
}

inline fun View.setOnSingleClickListener(
    delay: Long = 500L,
    crossinline block: (View) -> Unit,
) {
    var previousClickedTime = 0L
    setOnClickListener { view ->
        val clickedTime = System.currentTimeMillis()
        if (clickedTime - previousClickedTime >= delay) {
            block(view)
            previousClickedTime = clickedTime
        }
    }
}
