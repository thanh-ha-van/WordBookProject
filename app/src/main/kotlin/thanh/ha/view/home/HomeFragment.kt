package thanh.ha.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.ui.adapters.KeywordAdapter
import thanh.ha.ui.adapters.MyItemTouchHelper
import thanh.ha.ui.adapters.SmallWordDefAdapter


class HomeFragment(private var keywordPasser: KeywordPasser) : BaseFragment(),
        SmallWordDefAdapter.ClickListener,
        KeywordAdapter.ClickListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private var mDisposable: Disposable? = null
    private lateinit var savedWordAdapter: SmallWordDefAdapter
    private lateinit var recentSearchAdapter: KeywordAdapter
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
        getLocalRecentKeyword()

    }
    private fun initView() {

        //set up for save definitions adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_saved_def.layoutManager = layoutManager

        savedWordAdapter = SmallWordDefAdapter(context, this)
        rv_saved_def.layoutManager = layoutManager
        rv_saved_def.adapter = savedWordAdapter

        val swipeHandler = object : MyItemTouchHelper(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_saved_def.adapter as SmallWordDefAdapter
                mHomeViewModel.removeLocal(
                        adapter.getItemAt(viewHolder.adapterPosition)
                )
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_saved_def)
        //set up for recent search keyword adapter
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

        recentSearchAdapter = KeywordAdapter(context, this)
        rv_recent.layoutManager = layoutManager2
        rv_recent.adapter = recentSearchAdapter
        rv_recent.setItemViewCacheSize(10)
        tv_clear_saved.setOnClickListener {
            saved_def_desc.visibility = View.VISIBLE
            mHomeViewModel.deleteAllLocalWord()
        }

        tv_clear_search.setOnClickListener {
            tv_recent_search_desc.visibility = View.VISIBLE
            mHomeViewModel.deleteAllKeyword()
        }
    }

    private fun getLocalRecentKeyword() {
        mHomeViewModel.getLocalKeyword()?.observe(
                viewLifecycleOwner,
                Observer {
                    if (it.isNullOrEmpty()) {
                        tv_recent_search_desc.visibility = View.VISIBLE
                    } else {
                        tv_recent_search_desc.visibility = View.GONE
                    }
                    recentSearchAdapter.updateInfo(it.toMutableList())
                }
        )
    }

    override fun onClickKeyWord(string: String) {
        keywordPasser.onPassKeyword(string)
    }

    private fun getLocalSaved() {
        mHomeViewModel.getLocalDefinitions()?.observe(
                viewLifecycleOwner,
                Observer {
                    if (it.isNullOrEmpty()) {
                        saved_def_desc.visibility = View.VISIBLE
                    } else {
                        saved_def_desc.visibility = View.GONE
                    }
                    savedWordAdapter.updateInfo(it.toMutableList())
                }
        )
    }

    override fun onResume() {
        super.onResume()
        if (recentSearchAdapter.itemCount > 0)
            rv_recent.smoothScrollToPosition(recentSearchAdapter.itemCount - 1)
        if (savedWordAdapter.itemCount > 0)
            rv_saved_def.smoothScrollToPosition(0)
    }

    private fun initViewModel() {
        mHomeViewModel = ViewModelProvider(this)
                .get(HomeViewModel::class.java)
        mHomeViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposable != null && mDisposable?.isDisposed!!) mDisposable?.dispose()
    }
}
