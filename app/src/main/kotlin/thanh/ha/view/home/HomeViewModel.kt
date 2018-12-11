package thanh.ha.view.home

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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

    fun saveDefToLocal(definitionInfo: DefinitionInfo) {
        definitionRepository.saveLocalDefs(definitionInfo)
    }

    fun deleteAllLocalWord() {
        definitionRepository.getLocalDefs()

    }
}
