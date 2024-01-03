package sopt.motivoo.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import coil.Coil
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showSnackbar(view: View, message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(view, message, duration).show()
}

fun Context.colorOf(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.drawableOf(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)

fun Context.loadingImage(imageUrl: String, imageView: ImageView, loadingImage: Int) {
    imageUrl.let { url ->
        val imageRequest = ImageRequest.Builder(this)
            .data(url)
            .placeholder(loadingImage)
            .target(imageView)
            .build()
        Coil.imageLoader(this).enqueue(imageRequest)
    }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}
