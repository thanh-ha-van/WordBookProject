package thanh.ha.view.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_definition.*
import thanh.ha.R
import thanh.ha.ui.adapters.DefAdapter
import thanh.ha.ui.dialogs.LoadingDialog


class DefinitionFragment : Fragment(), DefAdapter.ClickListener {

    companion object {
        fun newInstance() = DefinitionFragment()
    }

    private lateinit var definitionViewModel: DefinitionViewModel
    private lateinit var adapter: DefAdapter
    private lateinit var dialog: LoadingDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
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
            searchKeyword(et_search.text.toString().trim())
        }
        dialog = LoadingDialog(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_definition,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        lastSearch()
    }

    private fun lastSearch() {
        //TODO show last search result, some working with database. far future.
    }

    private fun initViewModel() {
        definitionViewModel = ViewModelProviders
                .of(this)
                .get(DefinitionViewModel::class.java)
        definitionViewModel
                .let {
                    lifecycle.addObserver(it)
                }
    }

    private fun searchKeyword(word: String) {
        showLoadingDialog()
        definitionViewModel
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

    private fun showLoadingDialog() {
        dialog.showDialog()
    }

    private fun hideLoadingDialog() {
        dialog.hideDialog()
    }

    override fun onThumbUp(position: Int) {
    }

    override fun onThumbUpDown(position: Int) {

    }

    override fun onClickKeyWord(string: String) {
        et_search.setText(string)
        searchKeyword(string)
    }
}
