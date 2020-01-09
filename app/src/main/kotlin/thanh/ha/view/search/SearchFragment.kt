package thanh.ha.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_search.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.domain.DefinitionInfo


class SearchFragment : BaseFragment(), CardFragment.KeyWordListener {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var mCardAdapter: CardFragmentPagerAdapter
    private lateinit var mCardShadowTransformer: ShadowTransformer

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
        shimmerFrame.visibility = View.VISIBLE
        shimmerFrame.showShimmer(true)
    }

    private fun hideLoading() {
        shimmerFrame.stopShimmer()
        shimmerFrame.visibility = View.GONE
    }

    private fun initView() {
        mCardAdapter = CardFragmentPagerAdapter(childFragmentManager)
        mCardShadowTransformer = ShadowTransformer(vp_definition, mCardAdapter)
        mCardShadowTransformer.enableScaling(true)
        vp_definition.adapter = mCardAdapter
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

    private fun updateRecentSearch(word: String) {
        RxBus.publish(RxEvent.EventRecentSearch(word))
    }

    private fun getRandom() {
        mCardAdapter.clear()
        showLoading()
        searchViewModel.getRandom()?.observe(
                this,
                Observer {
                    hideLoading()
                    for (item in it) {
                        mCardAdapter.addCardFragment(CardFragment(item, this))
                    }
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
        mCardAdapter.clear()
        showLoading()
        updateRecentSearch(word)
        searchViewModel
                .getWordDefinition(word)?.observe(
                        this,
                        Observer {
                            hideLoading()
                            for (item in it) {
                                mCardAdapter.addCardFragment(CardFragment(item, this))
                            }
                        })
    }


    override fun onKeyWordClicked(string: String) {
        et_search.setText(string)
        searchKeyword(string)
    }

    override fun onThumbUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onThumbUpDown() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSaveClicked(def: DefinitionInfo) {
        searchViewModel.saveDefToLocal(def)
    }

    override fun onUnSaveClicked(def: DefinitionInfo) {
        searchViewModel.removeDefFromLocal(def)
    }

}
