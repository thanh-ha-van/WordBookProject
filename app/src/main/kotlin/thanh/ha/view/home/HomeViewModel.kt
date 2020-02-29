package thanh.ha.view.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import thanh.ha.data.repository.DefinitionRepository
import thanh.ha.di.WordBookApp
import thanh.ha.domain.DefinitionInfo
import thanh.ha.domain.Keyword
import javax.inject.Inject

class HomeViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var definitionRepository: DefinitionRepository

    init {
        WordBookApp.appComponent.inject(this)
    }

    fun getLocalDefinitions(): LiveData<List<DefinitionInfo>>? {
        return definitionRepository.getLocalDefs()
    }

    fun deleteAllLocalWord() {
        definitionRepository.deleteAllDefs()
    }

    fun getLocalKeyword(): LiveData<List<Keyword>>? {
        return definitionRepository.getLocalKeyword()
    }

    fun deleteAllKeyword() {
        definitionRepository.deleteAllKeyword()
    }
}
