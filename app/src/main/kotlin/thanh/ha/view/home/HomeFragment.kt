package thanh.ha.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.ui.adapters.SmallWordDefAdapter


class HomeFragment : BaseFragment(), SmallWordDefAdapter.ClickListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mDisposable: Disposable
    private lateinit var adapter: SmallWordDefAdapter

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

    private fun initView() {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        rv_saved_def.layoutManager = layoutManager
        adapter = SmallWordDefAdapter(context, this)
        rv_saved_def.adapter = adapter

        tv_clear_saved.setOnClickListener {
            saved_def_desc.visibility = View.VISIBLE
            mHomeViewModel.deleteAllLocalWord()
            adapter.clear()
        }
        tv_clear_search.setOnClickListener {
            cg_recent_search.clearCheck()
        }
    }

    override fun onClickKeyWord(string: String) {

    }

    private fun getRecentSearch() {
        mDisposable = RxBus
                .listen(RxEvent.EventRecentSearch::class.java)
                .subscribe {
                    tv_recent_search_desc.visibility = View.GONE
                    addChipToView(context, it.word)
                }
    }

    private fun addChipToView(context: Context?, def: String) {
        if (context != null) {
            val chip = Chip(context)
            chip.text = def
            chip.setOnCloseIconClickListener { cg_recent_search.removeView(chip) }
            cg_recent_search.addView(chip as View)
        }
    }

    private fun getLocalSaved() {
        mHomeViewModel.getLocalDefinitions()?.observe(
                this,
                Observer {
                    saved_def_desc.visibility = View.GONE
                    adapter.updateInfo(it.toMutableList())
                }
        )
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
