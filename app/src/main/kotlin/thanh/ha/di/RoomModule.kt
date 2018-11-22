package thanh.ha.di

import android.content.Context
import dagger.Module
import dagger.Provides
import thanh.ha.data.room.RoomCurrencyDataSource
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) =
            RoomCurrencyDataSource.buildPersistentCurrency(context)
}
