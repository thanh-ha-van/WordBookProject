package thanh.ha.data.remote

import com.example.DefinitionListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {

    @GET(RemoteContract.GET_WORD_END_POINT)
    fun requestWordDefinition(@Query(RemoteContract.TERM) word: String): Observable<DefinitionListResponse>

}


