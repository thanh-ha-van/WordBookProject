package thanh.ha.data.room

import androidx.room.*
import io.reactivex.Flowable
import thanh.ha.domain.Keyword


@Dao
interface RoomKeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeyword(def: Keyword)

    @Delete
    fun delete(def: Keyword)

    @Query(RoomContract.DELETE_KEYWORD)
    fun deleteAll()

    @Query(RoomContract.SELECT_KEYWORD)
    fun getAll(): Flowable<List<Keyword>>

    @Query(RoomContract.COUNT_KEYWORD)
    fun count(): Int

    @Transaction
    fun insertAndDeleteInTransaction(newProduct: Keyword, oldProduct: Keyword) {
        insertKeyword(newProduct)
        delete(oldProduct)
    }
}

