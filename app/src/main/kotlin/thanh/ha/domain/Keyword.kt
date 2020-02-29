package thanh.ha.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import thanh.ha.data.room.RoomContract

@Entity(tableName = RoomContract.TABLE_KEYWORD)
data class Keyword(
        @PrimaryKey(autoGenerate = false)
        var word: String
)
