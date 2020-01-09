package thanh.ha.view.search

import androidx.lifecycle.*
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import io.reactivex.disposables.CompositeDisposable
import thanh.ha.data.repository.DefinitionRepository
import thanh.ha.di.WordBookApp
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.DoAsync
import javax.inject.Inject

class SearchViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var definitionRepository: DefinitionRepository

    private val compositeDisposable = CompositeDisposable()
    private var liveDefinitionData: LiveData<List<DefinitionInfo>>? = null

    init {
        WordBookApp.appComponent.inject(this)
    }

    fun getWordDefinition(info: String): LiveData<List<DefinitionInfo>>? {
        liveDefinitionData = null
        liveDefinitionData = MutableLiveData<List<DefinitionInfo>>()
        liveDefinitionData = definitionRepository.getWordDefs(info)
        return liveDefinitionData
    }

    fun getRandom(): LiveData<List<DefinitionInfo>>? {
        liveDefinitionData = null
        liveDefinitionData = MutableLiveData<List<DefinitionInfo>>()
        liveDefinitionData = definitionRepository.getRandom()
        return liveDefinitionData
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in definitionRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }


    fun saveDefToLocal(def: DefinitionInfo) {
        DoAsync {
            definitionRepository.saveLocalDefs(def)
        }.execute()

    }

    fun removeDefFromLocal(def: DefinitionInfo) {
        DoAsync {
            definitionRepository.removeLocalDefs(def)
        }.execute()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }
}
