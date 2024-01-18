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
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class MotivooMyPieChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {
    private var stepCount = 0f

    private val progressBarMyPaint: Paint = Paint()

    private val drawRectArea =
        RectF(LAYOUT_SPACING.px, LAYOUT_SPACING.px, DRAW_AREA_SIZE.px, DRAW_AREA_SIZE.px)

    private var myImage: Bitmap?

    private val layoutSize = LAYOUT_SIZE.px.toInt()

    init {
        myImage = ContextCompat.getDrawable(context, R.drawable.ic_child_user)?.run {
            toBitmap(IMAGE_SIZE.px.toInt(), IMAGE_SIZE.px.toInt())
        }

        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MotivooPieChart, defStyleAttr, defStyleAttr
        ).let { typedArray ->
            progressBarMyPaint.run {
                color = typedArray.getColor(R.styleable.MotivooPieChart_progressMyColor, Color.BLUE)
                style = Paint.Style.STROKE
                strokeWidth = 9.px
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

        canvas.drawArc(drawRectArea, START_ANGLE, ((stepCount) * DEGREE), false, progressBarMyPaint)
        drawImage(canvas)
    }

    private fun drawImage(canvas: Canvas) {
        val degree = abs(30 - (stepCount * DEGREE)).toDouble()
        val radian = Math.toRadians(degree)

        val x =
            (ORIGIN_VALUE.px - cos(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING - 3).px)
        val y: Float
        if ((30 - (stepCount * DEGREE)) >= 0) {
            y =
                (ORIGIN_VALUE.px + sin(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING).px).toFloat()
            myImage?.let { canvas.drawBitmap(it, x.toFloat(), y, null) }
        } else {
            y =
                (ORIGIN_VALUE.px - sin(radian) * RADIUS.px - (LAYOUT_SPACING + DIAMETER_SPACING).px).toFloat()
            if (degree <= 85) {
                myImage?.let { canvas.drawBitmap(it, x.toFloat(), y, null) }
            } else {
                myImage?.let {
                    canvas.drawBitmap(it, (x - 13.px).toFloat(), y, null)
                }
            }
        }
    }

    fun setStepCount(stepCount: Float) {
        if (stepCount * DEGREE >= DEGREE) {
            this.stepCount = 1f
        } else {
            this.stepCount = stepCount
        }
        invalidate()
    }

    fun setMyIcon(icon: Int) {
        this.myImage = ContextCompat.getDrawable(context, icon)?.run {
            toBitmap(IMAGE_SIZE.px.toInt(), IMAGE_SIZE.px.toInt())
        }
        invalidate()
    }

    fun successStepCount() {
        this.myImage = ContextCompat.getDrawable(context,R.drawable.ic_launcher_background )?.run {
            toBitmap(0.px.toInt(), 0.px.toInt())
        }
        invalidate()
    }


    companion object {
        private const val DIAMETER = 290 // fix
        private const val RADIUS = DIAMETER / 2
        private const val DIAMETER_SPACING = 15 // fix
        private const val STROKE_SIZE = 10 // even value (fix)
        private const val LAYOUT_SPACING = STROKE_SIZE / 2 + DIAMETER_SPACING
        private const val LAYOUT_SIZE = LAYOUT_SPACING * 2 + DIAMETER + DIAMETER_SPACING * 2

        private const val DRAW_AREA_SIZE = (LAYOUT_SPACING + DIAMETER + DIAMETER_SPACING * 2)

        private const val START_ANGLE = 150F // fix

        private const val IMAGE_SIZE = 48 // fix

        private const val DEGREE = 120

        private const val ORIGIN_VALUE = 180

        private const val SMALL_CIRCLE_Y = 20
    }
}
