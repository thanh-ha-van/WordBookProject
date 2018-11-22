package thanh.ha.data.repository

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import thanh.ha.domain.AvailableExchange
import thanh.ha.domain.Currency

interface Repository {

    fun getTotalCurrencies(): Flowable<Int>

    fun addCurrencies()

    fun getCurrencyList(): LiveData<List<Currency>>

    fun getAvailableExchange(currencies: String): LiveData<AvailableExchange>

}
