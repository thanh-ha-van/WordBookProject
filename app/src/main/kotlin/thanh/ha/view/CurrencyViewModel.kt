package thanh.ha.view

import android.arch.lifecycle.*
import android.arch.lifecycle.Lifecycle.Event.ON_DESTROY
import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import thanh.ha.data.repository.CurrencyRepository
import thanh.ha.di.CurrencyApplication
import thanh.ha.domain.AvailableExchange
import thanh.ha.domain.Currency
import javax.inject.Inject

class CurrencyViewModel : ViewModel(), LifecycleObserver {

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    private val compositeDisposable = CompositeDisposable()
    private var liveCurrencyData: LiveData<List<Currency>>? = null
    private var liveAvailableExchange: LiveData<AvailableExchange>? = null

    init {
        initializeDagger()
    }

    fun getAvailableExchange(currencies: String): LiveData<AvailableExchange>? {
        liveAvailableExchange = null
        liveAvailableExchange = MutableLiveData<AvailableExchange>()
        liveAvailableExchange = currencyRepository.getAvailableExchange(currencies)
        return liveAvailableExchange
    }

    fun loadCurrencyList(): LiveData<List<Currency>>? {
        if (liveCurrencyData == null) {
            liveCurrencyData = MutableLiveData<List<Currency>>()
            liveCurrencyData = currencyRepository.getCurrencyList()
        }
        return liveCurrencyData
    }

    fun initLocalCurrencies() {
        val disposable = currencyRepository.getTotalCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isRoomEmpty(it)) {
                        populate()
                    } else {
                        Log.i(CurrencyRepository::class.java.simpleName, "DataSource has been already Populated")
                    }
                }
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in currencyRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    private fun isRoomEmpty(currenciesTotal: Int) = currenciesTotal == 0

    private fun populate() {
        Completable.fromAction { currencyRepository.addCurrencies() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        Log.i(CurrencyRepository::class.java.simpleName, "DataSource has been Populated")

                    }

                    override fun onError(@NonNull e: Throwable) {
                        e.printStackTrace()
                        Log.e(CurrencyRepository::class.java.simpleName, "DataSource hasn't been Populated")
                    }
                })
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }

    private fun initializeDagger() = CurrencyApplication.appComponent.inject(this)

}
