package thanh.ha.data.remote

class RemoteContract {

    companion object {

        const val BASE_API = "http://api.urbandictionary.com/"

        private const val API_VERSION = "v0/"
        private const val DEFINE = "define"
        const val GET_WORD_END_POINT = API_VERSION + DEFINE
        const val TERM = "term"

    }
}


