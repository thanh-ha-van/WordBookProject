package thanh.ha.data.remote

class RemoteContract {

    companion object {

        const val BASE_API = "http://api.urbandictionary.com/"

        const val API_VERSION = "v0/"
        private const val DEFINE = "define"
        const val GET_WORD_END_POINT = API_VERSION + DEFINE
        // define a word
        const val TERM = "term"

        // fine a definition by id
        const val DEFID = "defid"

        // get a random definition
        const val RANDOM = "random"

        // page index of the definition list
        const val PAGE = "page"

    }
}


