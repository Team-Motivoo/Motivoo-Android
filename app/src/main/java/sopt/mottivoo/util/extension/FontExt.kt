package sopt.mottivoo.util.extension

import android.content.res.Resources

val Int.px: Float
    get() = this * Resources.getSystem().displayMetrics.density
