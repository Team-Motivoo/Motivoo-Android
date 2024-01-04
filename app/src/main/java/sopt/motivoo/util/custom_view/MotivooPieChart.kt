package sopt.motivoo.util.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import sopt.motivoo.R
import sopt.motivoo.util.extension.fromDpToPx

class MotivooPieChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {
    private var stepCount = 0f

    /**
     * wrap_content 대응을 위한 분기 처리 + 기기 화면 별 대응
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val layoutSize = LAYOUT_SIZE.fromDpToPx().toInt()

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

        val drawAreaSize = (LAYOUT_SPACING + DIAMETER + DIAMETER_SPACING * 2).fromDpToPx()
        val drawRectArea =
            RectF(LAYOUT_SPACING.toFloat(), LAYOUT_SPACING.toFloat(), drawAreaSize, drawAreaSize)

        /** 회색 원형 막대 */
        Paint().apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = STROKE_SIZE.toFloat()
        }.let {
            canvas.drawArc(drawRectArea, START_ANGLE, SWEEP_ANGLE, false, it)
            /** 하늘색 원형 막대 */
            it.color = Color.BLUE
            canvas.drawArc(drawRectArea, START_ANGLE, stepCount, false, it)
        }

        /** 로고 이미지 */
        context.getDrawable(R.drawable.ic_launcher_foreground)?.run {
            toBitmap(LOGO_IMAGE_SIZE.fromDpToPx().toInt(), LOGO_IMAGE_SIZE.fromDpToPx().toInt())
        }?.let {
            val logoSpacing = LOGO_IMAGE_SPACING.fromDpToPx()
            canvas.drawBitmap(it, logoSpacing, logoSpacing, null)
        }
    }

    /**
     * 걸음 수 (drawArc : sweepAngle 값으로 사용)
     * 원형 막대를 채우기 위한 걸음
     * sweepAngle : 360f (원 한 바퀴)
     * 현재 원에서는 320f 으로 원형 막대를 채운다.
     * 목표 걸음 : 10000
     * 현재 걸음 : 2000
     * 2000/10000 = 1/5 => stepCount = 1/5 * 320
     * drawArc(sweepAngle = stepCount)
     */
    fun setStepCount(stepCount: Float) {
        this.stepCount = stepCount
        invalidate() // 뷰를 다시 그려주어야 한다.
    }

    /**
     * LAYOUT_SIZE = LAYOUT_SPACING * 2 + DIAMETER + DIAMETER_SPACING * 2
     * LAYOUT_SPACING = STROKE_SIZE / 2 + DIAMETER_SPACING
     */
    companion object {
        private const val DIAMETER = 240 // fix
        private const val DIAMETER_SPACING = 2 // fix
        private const val STROKE_SIZE = 12 // even value (fix)
        private const val LAYOUT_SPACING = STROKE_SIZE / 2 + DIAMETER_SPACING
        private const val LAYOUT_SIZE = LAYOUT_SPACING * 2 + DIAMETER + DIAMETER_SPACING * 2

        private const val START_ANGLE = -115f
        private const val SWEEP_ANGLE = 320f

        private const val LOGO_IMAGE_SIZE = 48
        private const val LOGO_IMAGE_SPACING = LAYOUT_SPACING + 5
    }
}
