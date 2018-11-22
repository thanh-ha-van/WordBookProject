package thanh.ha.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface RoomDefinitionDao {

    @Insert
    fun insertAll(defList: List<DefEntity>)

    @Query(RoomContract.SELECT_DEFINITION)
    fun getAllLocalDefinition(): Flowable<List<DefEntity>>

}

