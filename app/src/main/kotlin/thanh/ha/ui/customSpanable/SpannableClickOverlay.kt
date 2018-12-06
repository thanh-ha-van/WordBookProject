package thanh.ha.ui.customSpanable

import android.text.style.ClickableSpan
import android.view.View


class SpannableClickOverlay(private val clickAction: SpannableClickAction,
                            val text: String) : ClickableSpan() {

    override fun onClick(view: View) {
        clickAction.onClick(text)
    }
}

