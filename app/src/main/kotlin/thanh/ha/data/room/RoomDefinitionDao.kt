package thanh.ha.data.room

import android.arch.persistence.room.*
import io.reactivex.Flowable
import thanh.ha.domain.DefinitionInfo

@Dao
interface RoomDefinitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDef(def: DefinitionInfo)

    @Delete
    fun deleteDef(def: DefinitionInfo)

    @Insert
    fun insertAllDefs(defList: List<DefinitionInfo>)

    @Query(RoomContract.SELECT_DEFINITION)
    fun getAllDefs(): Flowable<List<DefinitionInfo>>

}

