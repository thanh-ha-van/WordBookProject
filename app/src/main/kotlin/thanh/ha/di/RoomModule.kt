package thanh.ha.di

import android.content.Context
import dagger.Module
import dagger.Provides
import thanh.ha.data.room.RoomDataSource
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDataSource(context: Context) =
            RoomDataSource.buildDatabase(context)
}
