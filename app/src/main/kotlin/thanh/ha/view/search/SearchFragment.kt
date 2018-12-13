package thanh.ha.view.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_search.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.constants.Constants
import thanh.ha.helpers.getStringArrayPref
import thanh.ha.helpers.setStringArrayPref
import thanh.ha.ui.adapters.DefAdapter


class SearchFragment : BaseFragment(), DefAdapter.ClickListener {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: DefAdapter
    private var recentSearch = ArrayList<String>()

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
        getLastRecentSearch()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        rv_definition.layoutManager = layoutManager
        adapter = DefAdapter(context, this)
        rv_definition.adapter = adapter

        val animation = AnimationUtils
                .loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
        rv_definition.layoutAnimation = animation

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
            updateRecentSearch(text)
        }
    }

    private fun getRandom() {
        showLoadingDialog()
        searchViewModel.getRandom()?.observe(
                this,
                Observer {
                    adapter.updateInfo(it!!)
                    reRunAnimation()
                    rv_definition.smoothScrollToPosition(0)
                    hideLoadingDialog()
                }
        )
    }

    override fun onPause() {
        super.onPause()
        saveRecentSearch()
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
        showLoadingDialog()
        searchViewModel
                .getWordDefinition(word)?.observe(
                        this,
                        Observer { definitionList ->
                            adapter.updateInfo(definitionList!!)
                            reRunAnimation()
                            rv_definition.smoothScrollToPosition(0)
                            hideLoadingDialog()
                        })
    }

    private fun reRunAnimation() {
        rv_definition.scheduleLayoutAnimation()
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

    private fun getLastRecentSearch() {
        recentSearch = getStringArrayPref(context!!, Constants.SHARE_PREF)
    }

    private fun updateRecentSearch(word: String) {
        recentSearch.add(0, word)
    }

    private fun saveRecentSearch() {
        if (recentSearch.size > 10)
            recentSearch.subList(0, 10)
        setStringArrayPref(context!!, Constants.SHARE_PREF, recentSearch)
    }
}
