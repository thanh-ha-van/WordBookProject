package thanh.ha.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [DefEntity::class], version = 1,exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun currencyDao(): RoomDefinitionDao

    companion object {

        fun buildDatabase(context: Context): RoomDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomDataSource::class.java,
                RoomContract.DATABASE_DEFINITION
        ).build()

    }

}

