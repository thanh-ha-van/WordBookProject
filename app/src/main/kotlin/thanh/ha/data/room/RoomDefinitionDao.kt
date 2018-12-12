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

    @Query(RoomContract.DELETE_DEFINITION)
    fun deleteAllDefs()

    @Delete
    fun deleteById(def: DefinitionInfo)

    @Delete
    fun deleteDefsList(defs: List<DefinitionInfo>)

    @Insert
    fun insertAllDefs(defList: List<DefinitionInfo>)

    @Query(RoomContract.SELECT_DEFINITION)
    fun getAllDefs(): Flowable<List<DefinitionInfo>>

    @Query(RoomContract.COUNT)
    fun count(): Int

    @Transaction
    fun insertAndDeleteInTransaction(newProduct: DefinitionInfo, oldProduct: DefinitionInfo) {
        insertDef(newProduct)
        deleteDef(oldProduct)
    }
}

