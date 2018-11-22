package thanh.ha.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_CURRENCIES)
data class CurrencyEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var countryCode: String,
        var countryName: String
)

