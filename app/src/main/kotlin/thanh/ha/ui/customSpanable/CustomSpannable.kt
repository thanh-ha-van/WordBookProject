package thanh.ha.ui.customSpanable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.style.ReplacementSpan
import thanh.ha.R


class CustomSpannable(context: Context) : ReplacementSpan() {

    val cornerRadius: Float = 16f
    private val padding: Float = 2f

    private val backgroundColor = ContextCompat.getColor(context, R.color.gray_10)
    private val borderColor = ContextCompat.getColor(context, R.color.blue_70)


    override fun draw(
            canvas: Canvas,
            text: CharSequence,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
    ) {
        val rect = RectF(
                x + padding,
                top.toFloat() + padding,
                x + paint.measureText(text, start, end) + cornerRadius - padding,
                bottom.toFloat() - padding
        )
        // fill
        paint.color = backgroundColor
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(
                rect,
                cornerRadius,
                cornerRadius,
                paint
        )
        // text
        paint.color = borderColor
        canvas.drawText(
                text,
                start,
                end,
                x + cornerRadius / 2,
                y.toFloat(),
                paint
        )
        // border
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        canvas.drawRoundRect(
                rect,
                cornerRadius,
                cornerRadius,
                paint
        )
    }

    override fun getSize(
            paint: Paint,
            text: CharSequence,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
    ): Int {
        return Math.round(paint.measureText(text, start, end))
    }
}