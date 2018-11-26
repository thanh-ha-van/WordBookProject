package thanh.ha.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface RoomDefinitionDao {

    @Insert
    fun insertAll(defList: List<DefEntity>)

    @Query(RoomContract.SELECT_DEFINITION)
    fun getAllLocalDefinition(): Flowable<List<DefEntity>>

}

