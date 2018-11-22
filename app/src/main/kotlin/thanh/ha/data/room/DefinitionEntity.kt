package thanh.ha.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = RoomContract.TABLE_DEFINITION)
data class DefinitionEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var definitionRaw: String
)

