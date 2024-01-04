package sopt.motivoo.util.extension

import android.content.res.Resources

fun Int.fromDpToPx(): Float =
    this * Resources.getSystem().displayMetrics.density
