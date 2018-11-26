package thanh.ha.utitls

import java.util.regex.Pattern

object StringUtils {
    fun ggez(text: String): String {
        var fff = String()
        val m = Pattern.compile("\\[(.*?)]")
                .matcher(text)
        while (m.find()) {
            fff += (m.group())
        }
        return fff
    }
}