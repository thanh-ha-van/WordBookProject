package thanh.ha.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import thanh.ha.domain.DefinitionInfo

@Database(entities = [DefinitionInfo::class], version = 1, exportSchema = false)
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

