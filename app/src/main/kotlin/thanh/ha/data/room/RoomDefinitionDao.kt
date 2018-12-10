package thanh.ha.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface RoomDefinitionDao {

    @Insert
    fun insertAll(defList: List<DefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDef(def: DefEntity)

    @Delete
    fun deleteUsers(def: DefEntity)

    @Query(RoomContract.SELECT_DEFINITION)
    fun getAllLocalDefinition(): Flowable<List<DefEntity>>

}

