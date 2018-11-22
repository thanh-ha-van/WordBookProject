package thanh.ha.view.search

import android.arch.lifecycle.*
import android.arch.lifecycle.Lifecycle.Event.ON_DESTROY
import io.reactivex.disposables.CompositeDisposable
import thanh.ha.data.repository.DefinitionRepository
import thanh.ha.di.WordBookApp
import thanh.ha.domain.DefinitionInfo
import javax.inject.Inject

class DefinitionViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var definitionRepository: DefinitionRepository

    private val compositeDisposable = CompositeDisposable()
    private var liveCurrencyData: LiveData<List<DefinitionInfo>>? = null

    init {
        initializeDagger()
    }

    fun getWordDefinition(info: String): LiveData<List<DefinitionInfo>>? {
        liveCurrencyData = null
        liveCurrencyData = MutableLiveData<List<DefinitionInfo>>()
        liveCurrencyData = definitionRepository.getWordDefinition(info)
        return liveCurrencyData
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in definitionRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

    private fun initializeDagger() = WordBookApp.appComponent.inject(this)

//    private fun isRoomEmpty(currenciesTotal: Int) = currenciesTotal == 0
//
//    private fun populate() {
//        Completable.fromAction { definitionRepository.addCurrencies() }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : CompletableObserver {
//                    override fun onSubscribe(@NonNull d: Disposable) {
//                        compositeDisposable.add(d)
//                    }
//
//                    override fun onComplete() {
//                        Log.i(DefinitionRepository::class.java.simpleName, "DataSource has been Populated")
//
//                    }
//
//                    override fun onError(@NonNull e: Throwable) {
//                        e.printStackTrace()
//                        Log.e(DefinitionRepository::class.java.simpleName, "DataSource hasn't been Populated")
//                    }
//                })
//    }

//
//    fun initLocalCurrencies() {
//        val disposable = definitionRepository.getTotalCurrencies()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    if (isRoomEmpty(it)) {
//                        populate()
//                    } else {
//                        Log.i(DefinitionRepository::class.java.simpleName, "DataSource has been already Populated")
//                    }
//                }
//        compositeDisposable.add(disposable)
//    }
}
