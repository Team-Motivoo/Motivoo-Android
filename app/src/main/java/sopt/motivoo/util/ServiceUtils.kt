package sopt.motivoo.util

import android.app.ActivityManager
import android.app.Service
import android.content.Context

fun <T> Service.isMyServiceRunning(serviceClass: Class<T>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    manager.getRunningServices(Integer.MAX_VALUE).forEach { services ->
        if (serviceClass.name.equals(services.service.className)) {
            return true
        }
    }
    return false
}
