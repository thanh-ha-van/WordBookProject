package thanh.ha.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_search.*
import thanh.ha.R
import thanh.ha.base.BaseFragment
import thanh.ha.bus.RxBus
import thanh.ha.bus.RxEvent
import thanh.ha.ui.adapters.DefAdapter


class SearchFragment : BaseFragment(), DefAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

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

    private fun initView() {

        mCardAdapter = CardFragmentPagerAdapter(fragmentManager, dpToPixels(2, context!!))
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

    fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.getResources().getDisplayMetrics().density
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
                    for (item in it) {
                        mCardAdapter.addCardFragment(CardFragment(item))
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
        showLoadingDialog()
        updateRecentSearch(word)
        searchViewModel
                .getWordDefinition(word)?.observe(
                        this,
                        Observer {
                            hideLoadingDialog()
                            for (item in it) {
                                mCardAdapter.addCardFragment(CardFragment(item))
                            }
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
