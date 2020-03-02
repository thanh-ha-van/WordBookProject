package thanh.ha.ui.textviews

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import thanh.ha.R


class GradientTextView : AppCompatTextView {

    private var mStartColor = 0
    private var mEndColor = 0

    private var shader: Shader? = null

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?,
                attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        if (attrs != null) {
            val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView)
            mStartColor = styledAttrs.getColor(R.styleable.GradientTextView_startColor,
                    ContextCompat.getColor(context, R.color.blue_90))
            mEndColor = styledAttrs.getColor(R.styleable.GradientTextView_endColor,
                    ContextCompat.getColor(context, R.color.blue_50))
            styledAttrs.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.color = (ContextCompat.getColor(context, R.color.white))
            paint.shader = LinearGradient(0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    mStartColor,
                    mEndColor,
                    Shader.TileMode.CLAMP)
        }
    }

    fun setColors(startColor: Int, endColor: Int) {
        mStartColor = startColor
        mEndColor = endColor
        invalidate()
    }


    override fun onDetachedFromWindow() {
        shader = null
        super.onDetachedFromWindow()
    }
}