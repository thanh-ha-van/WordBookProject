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
                LinearLayoutManager.HORIZONTAL, false)
        rv_definition.layoutManager = layoutManager
        adapter = DefAdapter(context, this)
        rv_definition.adapter = adapter
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
    }

    override fun onRefresh() {
        getRandom()
    }

    private fun updateRecentSearch(word: String) {
        RxBus.publish(RxEvent.EventRecentSearch(word))
    }

    private fun getRandom() {
        showLoadingDialog()
        searchViewModel.getRandom()?.observe(
                this,
                Observer {
                    hideLoadingDialog()
                    adapter.updateInfo(it!!)
                    rv_definition.smoothScrollToPosition(0)
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
        showLoadingDialog()
        updateRecentSearch(word)
        searchViewModel
                .getWordDefinition(word)?.observe(
                        this,
                        Observer { definitionList ->
                            hideLoadingDialog()
                            adapter.updateInfo(definitionList!!)
                            rv_definition.smoothScrollToPosition(0)
                        })
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
