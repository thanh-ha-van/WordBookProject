package thanh.ha.view.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.chip.Chip
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.domain.DefinitionInfo

class HomeFragment : BaseFragment() {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocalSaved()
        getRecentSearch()
    }

    private fun getRecentSearch() {

        mDisposable = RxBus
                .listen(RxEvent.EventRecentSearch::class.java)
                .subscribe {
                    val chip = Chip(context)
                    chip.text = it.word
                    cg_recent_search.addView(chip as View)
                }
        mDisposable = RxBus
                .listen(RxEvent.EventSavedLocal::class.java)
                .subscribe {
                    val chip = Chip(context)
                    chip.text = it.word.word
                    cg_recent_search.addView(chip as View)
                }
    }

    private fun getLocalSaved() {
        mHomeViewModel.getLocalDefinitions()?.observe(
                this,
                Observer {
                    addLocalSavedToChip(it!!)
                }
        )
    }

    private fun addLocalSavedToChip(data: List<DefinitionInfo>) {
        for (item in data) {
            val chip = Chip(context)
            chip.text = item.word
            chip.isClickable = true
            chip.isCheckable = false
            cg_saved_def.addView(chip as View)
        }
    }

    private fun initViewModel() {
        mHomeViewModel = ViewModelProviders
                .of(this)
                .get(HomeViewModel::class.java)
        mHomeViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mDisposable.isDisposed) mDisposable.dispose()
    }
}
