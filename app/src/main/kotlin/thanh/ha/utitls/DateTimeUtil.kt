package thanh.ha.utitls

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateTimeUtil {
    @Throws(ParseException::class)
    fun convertToNewFormat(dateStr: String): String {
        val utc = TimeZone.getTimeZone("UTC")
        val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val destFormat = SimpleDateFormat("yyyy MMM dd")
        sourceFormat.timeZone = utc
        val convertedDate = sourceFormat.parse(dateStr)
        return destFormat.format(convertedDate)
    }
}