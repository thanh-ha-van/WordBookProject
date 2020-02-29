package thanh.ha.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import thanh.ha.domain.DefinitionInfo
import thanh.ha.domain.Keyword

@Database(entities = [DefinitionInfo::class, Keyword::class], version = 2, exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun definitionDao(): RoomDefinitionDao
    abstract fun keywordDao(): RoomKeywordDao

    companion object {

        fun buildDatabase(context: Context): RoomDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomDataSource::class.java,
                RoomContract.DATABASE_DEFINITION
        ).fallbackToDestructiveMigration().build()

    }
}

