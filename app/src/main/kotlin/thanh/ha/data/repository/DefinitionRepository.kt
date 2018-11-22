package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.DefinitionListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thanh.ha.data.remote.RemoteDataSource
import thanh.ha.data.room.RoomDataSource
import thanh.ha.domain.Definition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefinitionRepository @Inject constructor(
        private val roomDataSource: RoomDataSource,
        private val remoteDataSource: RemoteDataSource
) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()


    override fun getWordDefinition(currencies: String): LiveData<List<Definition>> {
        val mutableLiveData = MutableLiveData<List<Definition>>()
        val disposable = remoteDataSource.requestWordDefinition(currencies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyResponse ->

                    mutableLiveData.value = transform(currencyResponse)

                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(response: DefinitionListResponse): List<Definition> {
        val currencyList = ArrayList<Definition>()
        response.list?.forEach {
            currencyList.add(Definition(it.toString()))
        }
        return currencyList
    }

}
