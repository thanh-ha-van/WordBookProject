package thanh.ha.data.error


class APIError {

    private val statusCode: Int = 0
    private val message: String? = null

    fun status(): Int {
        return statusCode
    }

    fun message(): String? {
        return message
    }
}