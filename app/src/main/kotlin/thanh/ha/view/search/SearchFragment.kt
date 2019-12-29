package thanh.ha.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_search.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.ui.adapters.DefAdapter


class SearchFragment : BaseFragment(), DefAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: DefAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getRandom()
    }

    private fun initView() {

        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        rv_definition.layoutManager = layoutManager
        adapter = DefAdapter(context, this)
        rv_definition.adapter = adapter

//        val animation = AnimationUtils
//                .loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
//        rv_definition.layoutAnimation = animation

        et_search.setOnEditorActionListener(
                TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        searchKeyword(et_search.text.toString().trim())
                        return@OnEditorActionListener true
                    }
                    false
                })
        btn_search.setOnClickListener {
            val text = et_search.text.toString().trim()
            searchKeyword(text)
        }
        swipe_refresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        getRandom()
    }

    private fun updateRecentSearch(word: String) {
        RxBus.publish(RxEvent.EventRecentSearch(word))
    }

    private fun getRandom() {
        swipe_refresh.isRefreshing = true
        searchViewModel.getRandom()?.observe(
                this,
                Observer {
                    adapter.updateInfo(it!!)
                    reRunAnimation()
                    rv_definition.smoothScrollToPosition(0)
                    swipe_refresh.isRefreshing = false
                }
        )
    }


    private fun initViewModel() {
        searchViewModel = ViewModelProviders
                .of(this)
                .get(SearchViewModel::class.java)
        searchViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    private fun searchKeyword(word: String) {
        if (word.isEmpty()) return
        updateRecentSearch(word)
        swipe_refresh.isRefreshing = true
        searchViewModel
                .getWordDefinition(word)?.observe(
                        this,
                        Observer { definitionList ->
                            adapter.updateInfo(definitionList!!)
                            reRunAnimation()
                            rv_definition.smoothScrollToPosition(0)
                            swipe_refresh.isRefreshing = false
                        })
    }

    private fun reRunAnimation() {
        //rv_definition.scheduleLayoutAnimation()
    }

    override fun onThumbUp(position: Int) {
    }

    override fun onThumbUpDown(position: Int) {

    }

    override fun onSaveClicked(position: Int) {
        searchViewModel.saveDefToLocal(position)
    }

    override fun onUnSaveClicked(position: Int) {
        searchViewModel.removeDefFromLocal(position)
    }

    override fun onClickKeyWord(string: String) {
        et_search.setText(string)
        searchKeyword(string)
    }
}
