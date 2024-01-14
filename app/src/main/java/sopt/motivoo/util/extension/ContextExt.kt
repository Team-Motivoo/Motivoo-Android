package sopt.motivoo.util.extension

import android.app.Activity
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private const val NOTIFICATION_ID = 0

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

fun Context.showKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.sendNotification(
    messageBody: String
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
        setContentTitle(
            applicationContext.getString(R.string.step_count_notification_channel_title)
        )
        setContentText(messageBody)
        setContentIntent(contentPendingIntent)
        setAutoCancel(true)
        setOngoing(true)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
    }.build()
}

// TODO:: 비트맵 파일 변환 예시 코드
fun Context.bitmapToFile(bitmap: Bitmap, saveName: String): File {
    val saveDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        .toString() + saveName
    val file = File(saveDir)
    if (!file.exists()) file.mkdirs()

    val fileName = "$saveName.jpg"
    val tempFile = File(saveDir, fileName)

    var out: OutputStream? = null
    try {
        if (tempFile.createNewFile()) {
            out = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
    } finally {
        out?.close()
    }
    return tempFile
}
