package com.android.motivoo_design

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.utils.BitmapCacheManager
import com.android.utils.Child
import com.android.utils.Parent
import com.android.utils.PieChartUserType
import com.android.utils.px
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class MotivooOtherPieChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {
    private val innerProgressBarPaint: Paint = Paint()
    private val otherProgressBarPaint: Paint = Paint()
    private val centerCirclePaint: Paint = Paint()
    private var percent = 0f
    private var degree = 0.0
    private var otherImageBitmap: Bitmap? = null
    private var x: Double = 0.0
    private var y: Double = 0.0

    private val drawRectArea =
        RectF(LAYOUT_SPACING.px, LAYOUT_SPACING.px, DRAW_AREA_SIZE.px, DRAW_AREA_SIZE.px)

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    var userType: PieChartUserType? = null
        set(value) {
            when (value) {
                Child -> {
                    setOtherImageBitmap(R.drawable.ic_child_other)
                }

                Parent -> {
                    setOtherImageBitmap(R.drawable.ic_parent_other)
                }

                else -> Unit
            }
            field = value
        }

    init {
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MotivooPieChart, defStyleAttr, defStyleAttr
        ).let { typedArray ->
            innerProgressBarPaint.run {
                color =
                    typedArray.getColor(R.styleable.MotivooPieChart_progressInnerColor, Color.BLUE)
                style = Paint.Style.STROKE
                strokeWidth = STROKE_SIZE.px
            }
            otherProgressBarPaint.run {
                color =
                    typedArray.getColor(
                        R.styleable.MotivooPieChart_progressBackgroundColor,
                        Color.BLUE
                    )
                style = Paint.Style.STROKE
                strokeWidth = STROKE_SIZE.px
            }
            centerCirclePaint.run {
                color =
                    typedArray.getColor(R.styleable.MotivooPieChart_centerCircleColor, Color.BLACK)
                style = Paint.Style.STROKE
                strokeWidth = 2.px
            }
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measureMode(widthMeasureSpec) && measureMode(heightMeasureSpec)) {
            setMeasuredDimension(
                MeasureSpec.getMode(widthMeasureSpec),
                MeasureSpec.getMode(heightMeasureSpec)
            )
        } else {
            setMeasuredDimension(LAYOUT_SIZE.px.toInt(), LAYOUT_SIZE.px.toInt())
        }
    }

    private fun measureMode(measureSpec: Int): Boolean =
        MeasureSpec.getMode(measureSpec) == MeasureSpec.EXACTLY

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        scaleX = -1f

        canvas.drawArc(
            drawRectArea,
            START_ANGLE,
            FULL_ANGLE.toFloat(),
            false,
            innerProgressBarPaint
        )
        canvas.drawArc(
            drawRectArea,
            START_ANGLE, degree.toFloat(), false, otherProgressBarPaint
        )

        canvas.drawCircle((LAYOUT_SIZE / 2).px, LAYOUT_SPACING.px, 1.px, centerCirclePaint)

        otherImageBitmap?.let {
            canvas.drawBitmap(it, x.toFloat(), y.toFloat(), null)
        }
    }

    fun setPercent(percent: Float) {
        if (percent * SWEEP_ANGLE >= SWEEP_ANGLE) {
            this.percent = 1f
            degree = SWEEP_ANGLE
        } else {
            this.percent = percent
            degree = percent * SWEEP_ANGLE
        }
        invalidate()
    }

    fun setOtherImageBitmap(
        @DrawableRes imageDrawable: Int,
    ) {
        var radian: Double = if (degree - OVER_ANGLE < 0) {
            Math.toRadians(OVER_ANGLE - degree)
        } else {
            Math.toRadians(degree - OVER_ANGLE)
        }

        x =
            (LAYOUT_SIZE / 2).px - cos(radian) * RADIUS.px - (IMAGE_SIZE / 2).px
        y = if (degree - OVER_ANGLE < 0) {
            (LAYOUT_SIZE / 2).px + sin(radian) * RADIUS.px - (IMAGE_SIZE / 2).px
        } else {
            (LAYOUT_SIZE / 2).px - sin(radian) * RADIUS.px - (IMAGE_SIZE / 2).px
        }

        initImageDrawableToBitmap(imageDrawable)
        getBitmapCacheOtherImage()
        invalidate()
    }

    private fun initImageDrawableToBitmap(imageDrawable: Int) {
        if (otherImageBitmap == null) {
            otherImageBitmap = ContextCompat.getDrawable(context, imageDrawable)?.run {
                toBitmap(
                    IMAGE_SIZE.px.toInt(),
                    IMAGE_SIZE.px.toInt()
                )
            }
        }
    }

    private fun getBitmapCacheOtherImage() {
        scope.launch {
            BitmapCacheManager.apply {
                if (getInstance().getBitmapFromMemoryCache(OTHER_IMAGE) == null) {
                    otherImageBitmap?.let {
                        getInstance().addBitmapToMemoryCache(OTHER_IMAGE, it)
                    }
                } else {
                    otherImageBitmap = getInstance().getBitmapFromMemoryCache(OTHER_IMAGE)
                }
            }
        }
    }

    fun successStepCount(iconDrawable: Int?) {
        otherImageBitmap = if (iconDrawable == null) {
            null
        } else {
            ContextCompat.getDrawable(context, iconDrawable)?.run {
                toBitmap(IMAGE_SIZE.px.toInt(), IMAGE_SIZE.px.toInt())
            }
        }
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }

    companion object {
        private const val DIAMETER = 310 // fix
        private const val RADIUS = DIAMETER / 2
        private const val LAYOUT_SIZE = 360
        private const val STROKE_SIZE = 10 // even value (fix)
        private const val IMAGE_SIZE = 48 // fix
        private const val LAYOUT_SPACING = 25
        private const val DRAW_AREA_SIZE = DIAMETER + LAYOUT_SPACING
        private const val START_ANGLE = 150f // fix
        private const val OVER_ANGLE = 30.0
        private const val FULL_ANGLE = 240.0
        private const val CONFLICT_ANGLE = 8.7
        private const val SWEEP_ANGLE = (FULL_ANGLE / 2) - CONFLICT_ANGLE
    }
}
