package sopt.motivoo.util.custom_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import sopt.motivoo.R
import sopt.motivoo.util.extension.px
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class MotivooOtherPieChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private var stepCount = 0f

    private val progressBarInnerPaint: Paint = Paint()
    private val progressBarOtherPaint: Paint = Paint()

    private val centerCirclePaint: Paint = Paint()

    private val drawRectArea =
        RectF(LAYOUT_SPACING.px, LAYOUT_SPACING.px, DRAW_AREA_SIZE.px, DRAW_AREA_SIZE.px)

    private var otherImage: Bitmap?

    private val layoutSize = LAYOUT_SIZE.px.toInt()

    init {
        otherImage = ContextCompat.getDrawable(context, R.drawable.ic_parent_other)?.run {
            toBitmap(IMAGE_SIZE.px.toInt(), IMAGE_SIZE.px.toInt())
        }

        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MotivooPieChart, defStyleAttr, defStyleAttr
        ).let { typedArray ->
            progressBarInnerPaint.run {
                color = typedArray.getColor(R.styleable.MotivooPieChart_progressInnerColor, Color.BLUE)
                style = Paint.Style.STROKE
                strokeWidth = 9.px
            }
            progressBarOtherPaint.run {
                color = typedArray.getColor(R.styleable.MotivooPieChart_progressOtherColor, Color.BLUE)
                style = Paint.Style.STROKE
                strokeWidth = 9.px
            }
            centerCirclePaint.run {
                color = typedArray.getColor(R.styleable.MotivooPieChart_centerCicleColor, Color.BLACK)
                style = Paint.Style.STROKE
                strokeWidth = 3.px
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
            setMeasuredDimension(layoutSize, layoutSize)
        }
    }

    private fun measureMode(measureSpec: Int): Boolean =
        MeasureSpec.getMode(measureSpec) == MeasureSpec.EXACTLY

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        scaleX = -1f
        canvas.drawArc(drawRectArea, START_ANGLE, SWEEP_ANGLE, false, progressBarInnerPaint)
        canvas.drawArc(drawRectArea, START_ANGLE, ((stepCount) * DEGREE), false, progressBarOtherPaint)
        canvas.drawCircle(ORIGIN_VALUE.px, SMALL_CIRCLE_Y.px, 2.px, centerCirclePaint)
        drawImage(canvas)
    }

    private fun drawImage(canvas: Canvas) {
        val degree = abs(30 - (stepCount * DEGREE)).toDouble()
        val radian = Math.toRadians(degree)

        val x =
            (ORIGIN_VALUE.px - cos(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING).px)
        val y: Float
        if ((30 - (stepCount * DEGREE)) >= 0) {
            y = (ORIGIN_VALUE.px + sin(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING).px).toFloat()
            otherImage?.let { canvas.drawBitmap(it, x.toFloat(), y, null) }
        } else {
            y = (ORIGIN_VALUE.px - sin(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING).px).toFloat()
            if (degree <= 85) {
                otherImage?.let { canvas.drawBitmap(it, x.toFloat(), y, null) }
            } else {
                otherImage?.let {
                    canvas.drawBitmap(it, (x - 13.px).toFloat(), y, null)
                }
            }
        }
    }

    fun setStepCount(stepCount: Float) {
        this.stepCount = stepCount
        invalidate()
    }

    fun setOtherIcon(icon: Int) {
        otherImage = ContextCompat.getDrawable(context, icon)?.run {
            toBitmap(IMAGE_SIZE.px.toInt(), IMAGE_SIZE.px.toInt())
        }
    }

    companion object {
        private const val DIAMETER = 290 // fix
        private const val RADIUS = DIAMETER / 2
        private const val DIAMETER_SPACING = 15 // fix
        private const val STROKE_SIZE = 10 // even value (fix)
        private const val LAYOUT_SPACING = STROKE_SIZE / 2 + DIAMETER_SPACING
        private const val LAYOUT_SIZE = LAYOUT_SPACING * 2 + DIAMETER + DIAMETER_SPACING * 2

        private const val DRAW_AREA_SIZE = (LAYOUT_SPACING + DIAMETER + DIAMETER_SPACING * 2)

        private const val IMAGE_SIZE = 48 // fix

        private const val DEGREE = 120
        private const val START_ANGLE = 150F // fix
        private const val SWEEP_ANGLE = 240f

        private const val ORIGIN_VALUE = 180

        private const val SMALL_CIRCLE_Y = 20
    }
}
