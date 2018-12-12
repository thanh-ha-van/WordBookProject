package thanh.ha.data.remote

import com.example.DefinitionListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET(RemoteContract.GET_WORD_END_POINT)
    fun getWordDefinition(@Query(RemoteContract.TERM) word: String)
            : Observable<DefinitionListResponse>

    @GET(RemoteContract.GET_WORD_END_POINT)
    fun getWordDefinitionWithPage(
            @Query(RemoteContract.TERM) word: String,
            @Query(RemoteContract.PAGE) pageIndex: Int)
            : Observable<DefinitionListResponse>

    @GET(RemoteContract.GET_WORD_END_POINT)
    fun getDefinitionById(@Query(RemoteContract.DEFID) id: String)
            : Observable<DefinitionResponse>

    @GET(RemoteContract.API_VERSION + RemoteContract.RANDOM)
    fun getRandomDefintion()
            : Observable<DefinitionListResponse>
}


