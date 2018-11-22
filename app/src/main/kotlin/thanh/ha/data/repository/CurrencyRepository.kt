package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thanh.ha.data.remote.CurrencyResponse
import thanh.ha.data.remote.RemoteCurrencyDataSource
import thanh.ha.data.room.CurrencyEntity
import thanh.ha.data.room.RoomCurrencyDataSource
import thanh.ha.domain.AvailableExchange
import thanh.ha.domain.Currency
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
        private val roomCurrencyDataSource: RoomCurrencyDataSource,
        private val remoteCurrencyDataSource: RemoteCurrencyDataSource
) : Repository {

    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun getTotalCurrencies() = roomCurrencyDataSource.currencyDao().getCurrenciesTotal()

    override fun addCurrencies() {
        val currencyEntityList = RoomCurrencyDataSource.getAllCurrencies()
        roomCurrencyDataSource.currencyDao().insertAll(currencyEntityList)
    }

    override fun getCurrencyList(): LiveData<List<Currency>> {
        val roomCurrencyDao = roomCurrencyDataSource.currencyDao()
        val mutableLiveData = MutableLiveData<List<Currency>>()
        val disposable = roomCurrencyDao.getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyList ->
                    mutableLiveData.value = transform(currencyList)
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(currencies: List<CurrencyEntity>): List<Currency> {
        val currencyList = ArrayList<Currency>()
        currencies.forEach {
            currencyList.add(Currency(it.countryCode, it.countryName))
        }
        return currencyList
    }

    override fun getAvailableExchange(currencies: String): LiveData<AvailableExchange> {
        val mutableLiveData = MutableLiveData<AvailableExchange>()
        val disposable = remoteCurrencyDataSource.requestAvailableExchange(currencies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currencyResponse ->
                    if (currencyResponse.isSuccess) {
                        mutableLiveData.value = transform(currencyResponse)
                    } else {
                        throw Throwable("CurrencyRepository -> on Error occurred")
                    }
                }, { t: Throwable? -> t?.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    private fun transform(exchangeMap: CurrencyResponse): AvailableExchange {
        return AvailableExchange(exchangeMap.currencyQuotes)
    }

}
