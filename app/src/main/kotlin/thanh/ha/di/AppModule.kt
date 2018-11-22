package thanh.ha.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val wordBookApp: WordBookApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = wordBookApp

}
