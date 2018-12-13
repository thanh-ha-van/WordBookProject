package thanh.ha.view.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.chip.Chip
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.ui.adapters.SavedDefAdapter

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: SavedDefAdapter
    private lateinit var wordDisposable: Disposable

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
        initView()
        getLocalSaved()
        getRecentSearch()
    }

    private fun getRecentSearch() {

        wordDisposable = RxBus
                .listen(RxEvent.EventRecentSearch::class.java)
                .subscribe {
                    val chip = Chip(context)
                    chip.text = it.word
                    chip.isClickable = true
                    chip.isCheckable = false
                    chipGroup.addView(chip as View)
                }
    }

    private fun getLocalSaved() {
        homeViewModel.getLocalDefinitions()?.observe(
                this,
                Observer {
                    adapter.updateInfo(it!!)
                    rv_saved_def.smoothScrollToPosition(0)
                    hideLoadingDialog()
                }
        )
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        adapter = SavedDefAdapter(context, null)
        rv_saved_def.layoutManager = layoutManager
        rv_saved_def.adapter = adapter
        val animation = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        rv_saved_def.layoutAnimation = animation
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProviders
                .of(this)
                .get(HomeViewModel::class.java)
        homeViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!wordDisposable.isDisposed) wordDisposable.dispose()
    }
}
