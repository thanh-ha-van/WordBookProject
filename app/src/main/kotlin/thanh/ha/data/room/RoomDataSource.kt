package thanh.ha.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DefEntity::class], version = 1, exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun currencyDao(): RoomDefinitionDao

    companion object {

        fun buildDatabase(context: Context): RoomDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomDataSource::class.java,
                RoomContract.DATABASE_DEFINITION
        ).build()

        //TODO use this to catch data to local, save search history...
    }

}

