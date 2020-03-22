package thanh.ha.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_search.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.domain.DefinitionInfo
import thanh.ha.ui.adapters.DefAdapter


class SearchFragment : BaseFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        DefAdapter.ClickListener {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var mCardAdapter: DefAdapter

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


    private fun showLoading() {
        swipe_layout.isRefreshing = true
    }

    private fun hideLoading() {
        swipe_layout.isRefreshing = false
    }

    fun onSearchIntent(string: String) {
        et_search.setText(string)
        searchKeyword(string)
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        rv_definition.layoutManager = layoutManager

        mCardAdapter = DefAdapter(context, this)
        rv_definition.adapter = mCardAdapter

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
        swipe_layout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        if (et_search.text.isNullOrEmpty()) {
            getRandom()
        } else {
            searchKeyword(et_search.text.toString())
        }
    }

    private fun updateRecentSearch(word: String) {
        searchViewModel.saveRecent(word)
    }

    private fun getRandom() {
        showLoading()
        searchViewModel.getRandom()?.observe(
                viewLifecycleOwner,
                Observer {
                    bindResult(it)
                }
        )
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProvider(this)
                .get(SearchViewModel::class.java)
        searchViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    private fun searchKeyword(word: String) {
        if (word.isEmpty()) return
        showLoading()
        updateRecentSearch(word)
        searchViewModel
                .getWordDefinition(word)?.observe(
                        viewLifecycleOwner,
                        Observer {
                            bindResult(it)
                        })
    }

    private fun bindResult(it: List<DefinitionInfo>) {
        mCardAdapter.updateInfo(newItems = it)
        rv_definition.smoothScrollToPosition(0)
        hideLoading()
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
