package thanh.ha.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey


//TODO consider update this duplicated classes.
@Entity(tableName = RoomContract.TABLE_DEFINITION)
data class DefEntity(
        @PrimaryKey(autoGenerate = false) val defId: Int,
        var definition: String,
        var thumbsUp: Int,
        var thumbsDown: Int,
        var author: String,
        var word: String,
        var currentVote: Int,
        var writtenOn: String,
        var example: String
)

