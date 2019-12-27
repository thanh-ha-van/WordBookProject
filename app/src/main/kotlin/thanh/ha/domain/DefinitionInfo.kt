package thanh.ha.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import thanh.ha.data.room.RoomContract

@Entity(tableName = RoomContract.TABLE_DEFINITION)
data class DefinitionInfo(
        @PrimaryKey(autoGenerate = false) var defId: Int,
        var word: String,
        var definition: String,
        var thumbsUp: Int?,
        var thumbsDown: Int?,
        var author: String,
        var writtenOn: String?,
        var example: String?
)
