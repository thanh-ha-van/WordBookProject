package thanh.ha.ui.customSpanable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.text.style.ReplacementSpan
import thanh.ha.R

class CustomSpannable(context: Context) : ReplacementSpan() {
    val cornerRadius = 16
    private val backgroundColor = ContextCompat.getColor(context, R.color.orange_50)
    private val textColor = ContextCompat.getColor(context, R.color.white)
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
                x,
                top.toFloat(),
                x + paint.measureText(text, start, end) + cornerRadius.toFloat(),
                bottom.toFloat()
        )
        paint.color = backgroundColor
        canvas.drawRoundRect(
                rect,
                cornerRadius.toFloat(),
                cornerRadius.toFloat(),
                paint
        )
        paint.color = textColor
        canvas.drawText(
                text,
                start,
                end,
                x + cornerRadius / 2,
                y.toFloat(),
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