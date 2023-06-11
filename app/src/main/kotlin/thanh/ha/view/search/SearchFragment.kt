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
import thanh.ha.base.BaseFragment
import thanh.ha.databinding.FragmentSearchBinding
import thanh.ha.domain.DefinitionInfo
import thanh.ha.ui.adapters.DefAdapter


class SearchFragment : BaseFragment(),
    SwipeRefreshLayout.OnRefreshListener,
    DefAdapter.ClickListener {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var mCardAdapter: DefAdapter

    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getRandom()
    }


    private fun showLoading() {
        binding.swipeLayout.isRefreshing = true
    }

    private fun hideLoading() {
        binding.swipeLayout.isRefreshing = false
    }

    fun onSearchIntent(string: String) {
        binding.etSearch.setText(string)
        searchKeyword(string)
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        binding.rvDefinition.layoutManager = layoutManager

        mCardAdapter = DefAdapter(requireContext(), this)
        binding.rvDefinition.adapter = mCardAdapter

        binding.etSearch.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchKeyword(binding.etSearch.text.toString().trim())
                    return@OnEditorActionListener true
                }
                false
            })

        binding.btnSearch.setOnClickListener {
            val text = binding.etSearch.text.toString().trim()
            searchKeyword(text)
        }
        binding.swipeLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        if (binding.etSearch.text.isNullOrEmpty()) {
            getRandom()
        } else {
            searchKeyword(binding.etSearch.text.toString())
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
        binding.rvDefinition.smoothScrollToPosition(0)
        hideLoading()
    }

    override fun onSaveClicked(item: DefinitionInfo) {
        searchViewModel.saveDefToLocal(item)
    }

    override fun onUnSaveClicked(item: DefinitionInfo) {
        searchViewModel.removeDefFromLocal(item)
    }

    override fun onClickKeyWord(string: String) {
        binding.etSearch.setText(string)
        searchKeyword(string)
    }

}
