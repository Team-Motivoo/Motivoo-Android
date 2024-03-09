package com.android.utils

val Int.toMotivooUserType: MotivooUserType?
    get() = when (this) {
        1 -> Child
        2 -> Parent
        else -> null
    }