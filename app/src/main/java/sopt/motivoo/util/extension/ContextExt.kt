package sopt.motivoo.util.extension

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import coil.Coil
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import sopt.motivoo.R
import sopt.motivoo.presentation.MainActivity

private const val NOTIFICATION_ID = 0

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showSnackbar(view: View, message: String, actionMessage: String, isShort: Boolean = true, onClick: () -> Unit) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(view, message, duration)
        .setAction(actionMessage) { onClick() }
        .show()
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

fun Context.showKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.sendNotification(
    title: String,
    messageBody: String,
): Notification {
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_IMMUTABLE
    )

    return NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.step_count_notification_channel_id)
    ).apply {
        setSmallIcon(R.drawable.ic_clap_sound)
        setContentTitle(title)
        setContentText(messageBody)
        setContentIntent(contentPendingIntent)
        setAutoCancel(true)
        setOngoing(true)
        setShowWhen(false)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
    }.build()
}

fun Context.createUriToBitmap(photoUri: Uri): Bitmap =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source =
            ImageDecoder.createSource(contentResolver, photoUri)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(
            contentResolver,
            photoUri
        )
    }

fun Context.checkNetworkState(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNetwork.hasTransport(
        NetworkCapabilities.TRANSPORT_WIFI
    )
}

fun Context.redirectToLogin() {
    val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}
