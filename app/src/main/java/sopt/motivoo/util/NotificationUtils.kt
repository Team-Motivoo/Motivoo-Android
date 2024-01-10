package sopt.motivoo.util

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import sopt.motivoo.R
import sopt.motivoo.presentation.MainActivity

private val NOTIFICATION_ID = 0
fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context
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
