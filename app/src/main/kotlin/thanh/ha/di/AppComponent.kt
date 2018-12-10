package thanh.ha.di

import dagger.Component
import thanh.ha.view.home.HomeViewModel
import thanh.ha.view.search.SearchViewModel
import javax.inject.Singleton

@Component(modules = [AppModule::class, RoomModule::class, RemoteModule::class])
@Singleton
interface AppComponent {

    fun inject(searchViewModel: SearchViewModel)
    fun inject(homeViewModel: HomeViewModel)
}
