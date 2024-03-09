package sopt.motivoo.util.extension

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.View
import timber.log.Timber

fun View.updateBlurEffect() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    RenderEffect.createBlurEffect(
        10f, 10f, Shader.TileMode.CLAMP
    ).also { this.setRenderEffect(it) } else Timber.e("not supported blur effect")


fun View.removeBlurEffect() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    RenderEffect.createBlurEffect(
        10f, 10f, Shader.TileMode.CLAMP
    ).also { this.setRenderEffect(null) } else Timber.e("not supported blur effect")