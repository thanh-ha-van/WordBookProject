package thanh.ha.di

import android.app.Application
import thanh.ha.view.UserSetting


class WordBookApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var appSetting: UserSetting
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .roomModule(RoomModule())
                .remoteModule(RemoteModule()).build()
        appSetting = UserSetting(this)
    }
}

