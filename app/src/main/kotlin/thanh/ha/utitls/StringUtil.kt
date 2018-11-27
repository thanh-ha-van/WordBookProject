package thanh.ha.utitls

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import thanh.ha.ui.customSpanable.CustomSpannable
import java.util.regex.Pattern


object StringUtils {

    fun appSpirit(context: Context, text: String?): SpannableStringBuilder {
        var startIndex: Int
        var endIndex = 0
        val stringBuilder = SpannableStringBuilder()
        val m = Pattern.compile("\\[(.*?)]")
                .matcher(text)
        while (m.find()) {
            startIndex = m.start()
            stringBuilder.append(" " + text?.substring(endIndex, startIndex) + " ")
            endIndex = m.end()
            stringBuilder.append(m.group()
                    .replace("[", "")
                    .replace("]", ""))
            stringBuilder.setSpan(CustomSpannable(context),
                    startIndex + 2,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        stringBuilder.replace(0, 1, "")
        stringBuilder.append(" " + text?.subSequence(endIndex, text.length))
        return stringBuilder
    }

}