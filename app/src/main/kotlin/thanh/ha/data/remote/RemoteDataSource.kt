package thanh.ha.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val remoteService: RemoteService) {

    fun requestWordDefinition(word: String) = remoteService.requestWordDefinition(word)

}