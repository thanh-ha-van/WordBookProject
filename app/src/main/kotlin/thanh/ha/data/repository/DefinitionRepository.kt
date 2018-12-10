package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.DefinitionListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thanh.ha.data.remote.RemoteDataSource
import thanh.ha.data.room.DefEntity
import thanh.ha.data.room.RoomDataSource
import thanh.ha.domain.DefinitionInfo
import thanh.ha.utitls.DateTimeUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefinitionRepository @Inject constructor(
        private val roomDataSource: RoomDataSource,
        private val remoteDataSource: RemoteDataSource
) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    // Get word definitions from api.
    override fun getWordDefinition(currencies: String): LiveData<List<DefinitionInfo>> {
        val mutableLiveData = MutableLiveData<List<DefinitionInfo>>()
        val disposable =
                remoteDataSource.getWordDefinition(currencies)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { currencyResponse ->
                                    mutableLiveData.value = transform(currencyResponse)

                                },
                                { t: Throwable? ->
                                    t?.printStackTrace()
                                }
                        )
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }


    // get definitions from local
    override fun getLocalDefs(): LiveData<List<DefinitionInfo>> {
        val roomCurrencyDao = roomDataSource.currencyDao()
        val mutableLiveData = MutableLiveData<List<DefinitionInfo>>()
        val disposable = roomCurrencyDao.getAllLocalDefinition()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { currencyResponse ->
                            mutableLiveData.value = transform(currencyResponse)
                        },
                        { t: Throwable? ->
                            t?.printStackTrace()
                        })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    // transform Response from api to Info object
    private fun transform(response: DefinitionListResponse): List<DefinitionInfo> {
        val currencyList = ArrayList<DefinitionInfo>()
        response.list?.forEach {
            currencyList.add(
                    DefinitionInfo(
                            it.defid!!,
                            it.word!!,
                            it.definition!!,
                            it.thumbsUp,
                            it.thumbsDown,
                            it.author!!,
                            it.currentVote!!,
                            DateTimeUtil.convertToNewFormat(it.writtenOn!!),
                            it.example))
        }
        return currencyList
    }


    // transform from local data to object.
    private fun transform(response: List<DefEntity>): List<DefinitionInfo> {
        val currencyList = ArrayList<DefinitionInfo>()
        response.forEach {
            currencyList.add(
                    DefinitionInfo(
                            it.defId,
                            it.word,
                            it.definition,
                            it.thumbsUp,
                            it.thumbsDown,
                            it.author,
                            it.currentVote,
                            DateTimeUtil.convertToNewFormat(it.writtenOn),
                            it.example))
        }
        return currencyList
    }
}
