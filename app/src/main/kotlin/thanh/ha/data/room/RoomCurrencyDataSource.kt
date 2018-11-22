package thanh.ha.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(
        entities = [CurrencyEntity::class],
        version = 1)
abstract class RoomCurrencyDataSource : RoomDatabase() {

    abstract fun currencyDao(): RoomCurrencyDao

    companion object {

        fun buildPersistentCurrency(context: Context): RoomCurrencyDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomCurrencyDataSource::class.java,
                RoomContract.DATABASE_CURRENCY
        ).build()


        fun getAllCurrencies(): List<CurrencyEntity> {
            val mutableCurrencyList = mutableListOf<CurrencyEntity>()
            mutableCurrencyList.add(createCurrencyEntity("AED", "United Arab Emirates Dirham"))
            mutableCurrencyList.add(createCurrencyEntity("XPF", "CFP Franc"))
            mutableCurrencyList.add(createCurrencyEntity("YER", "Yemeni Rial"))
            mutableCurrencyList.add(createCurrencyEntity("ZAR", "South African Rand"))
            mutableCurrencyList.add(createCurrencyEntity("ZMK", "Zambian Kwacha (pre-2013)"))
            mutableCurrencyList.add(createCurrencyEntity("ZWL", "Zimbabwean Dollar"))
            return mutableCurrencyList
        }

        private fun createCurrencyEntity(countryCode: String, countryName: String) =
                CurrencyEntity(0, countryCode, countryName)
    }

}

