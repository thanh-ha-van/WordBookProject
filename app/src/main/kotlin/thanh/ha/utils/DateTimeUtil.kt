package thanh.ha.utils

import java.text.SimpleDateFormat
import java.util.*


fun convertToNewFormat(dateStr: String): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val destFormat = SimpleDateFormat("yyyy MMM dd", Locale.getDefault())
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    if (convertedDate != null)
        return destFormat.format(convertedDate)
    return "Unknown time"
}