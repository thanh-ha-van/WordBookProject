package thanh.ha.ui.customswitch

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import thanh.ha.R

/**
 * @author Thanh
 * @date 3/20/2020
 */

class MySwitch (context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.view_custom_switch, this)

//        val imageView: ImageView = findViewById(R.id.image)
//        val textView: TextView = findViewById(R.id.caption)
//
//        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BenefitView)
//        imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
//        textView.text = attributes.getString(R.styleable.BenefitView_text)
//        attributes.recycle()

    }
}