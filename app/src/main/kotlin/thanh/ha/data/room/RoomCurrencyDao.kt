package thanh.ha.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface RoomCurrencyDao {

    @Query(RoomContract.SELECT_CURRENCIES_COUNT)
    fun getCurrenciesTotal(): Flowable<Int>

    @Insert
    fun insertAll(currencies: List<CurrencyEntity>)

    @Query(RoomContract.SELECT_CURRENCIES)
    fun getAllCurrencies(): Flowable<List<CurrencyEntity>>

}

