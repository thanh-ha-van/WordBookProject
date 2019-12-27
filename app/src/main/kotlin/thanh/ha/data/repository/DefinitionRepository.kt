package thanh.ha.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.DefinitionListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thanh.ha.data.remote.RemoteService
import thanh.ha.data.room.RoomDataSource
import thanh.ha.domain.DefinitionInfo
import thanh.ha.utitls.DateTimeUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefinitionRepository
@Inject constructor(
        private val roomDataSource: RoomDataSource,
        private val remoteService: RemoteService
) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    // Get word definitions from api.
    override fun getWordDefs(word: String): LiveData<List<DefinitionInfo>> {
        val mutableLiveData = MutableLiveData<List<DefinitionInfo>>()
        val disposable =
                remoteService.getWordDefinition(word)
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

    // Get word definitions from api.
    override fun getRandom(): LiveData<List<DefinitionInfo>> {
        val mutableLiveData = MutableLiveData<List<DefinitionInfo>>()
        val disposable =
                remoteService.getRandomDefintion()
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
        val disposable = roomCurrencyDao.getAllDefs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { currencyResponse ->
                            mutableLiveData.value = currencyResponse
                        },
                        { t: Throwable? ->
                            t?.printStackTrace()
                        })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }


    override fun saveLocalDefs(definitionInfo: DefinitionInfo) {
        roomDataSource.currencyDao().insertDef(definitionInfo)
    }


    override fun removeLocalDefs(definitionInfo: DefinitionInfo) {
        roomDataSource.currencyDao().deleteDef(definitionInfo)
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
                            DateTimeUtil.convertToNewFormat(it.writtenOn!!),
                            it.example))
        }
        return currencyList
    }
}
