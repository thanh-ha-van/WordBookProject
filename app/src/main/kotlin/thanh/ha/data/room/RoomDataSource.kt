package thanh.ha.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [DefinitionEntity::class], version = 1,exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun currencyDao(): RoomDefinitionDao

    companion object {

        fun buildDatabase(context: Context): RoomDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomDataSource::class.java,
                RoomContract.DATABASE_DEFINITION
        ).build()

//
//        fun getAllLocalDefinition(): List<DefinitionEntity> {
//            val mutableCurrencyList = mutableListOf<DefinitionEntity>()
//            mutableCurrencyList.add(createCurrencyEntity("AED", "United Arab Emirates Dirham"))
//            mutableCurrencyList.add(createCurrencyEntity("XPF", "CFP Franc"))
//            mutableCurrencyList.add(createCurrencyEntity("YER", "Yemeni Rial"))
//            mutableCurrencyList.add(createCurrencyEntity("ZAR", "South African Rand"))
//            mutableCurrencyList.add(createCurrencyEntity("ZMK", "Zambian Kwacha (pre-2013)"))
//            mutableCurrencyList.add(createCurrencyEntity("ZWL", "Zimbabwean Dollar"))
//            return mutableCurrencyList
//        }
//
//        private fun createCurrencyEntity(countryCode: String, countryName: String) =
//                DefinitionEntity(0, countryCode, countryName)
    }

}

