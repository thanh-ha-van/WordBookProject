package thanh.ha.view.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thanh.ha.data.repository.DefinitionRepository
import thanh.ha.di.WordBookApp
import thanh.ha.domain.DefinitionInfo
import javax.inject.Inject

class HomeViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var definitionRepository: DefinitionRepository

    private var liveDefinitionData: LiveData<List<DefinitionInfo>>? = null

    init {
        WordBookApp.appComponent.inject(this)
    }

    fun getLocalDefinitions(): LiveData<List<DefinitionInfo>>? {
        liveDefinitionData = null
        liveDefinitionData = MutableLiveData<List<DefinitionInfo>>()
        liveDefinitionData = definitionRepository.getLocalDefs()
        return liveDefinitionData
    }

    fun deleteAllLocalWord() {

        definitionRepository.deleteAllDefs()

    }
}
