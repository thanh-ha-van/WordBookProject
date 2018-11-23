package thanh.ha.view.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_definition.*
import thanh.ha.R
import thanh.ha.ui.DefAdapter


class DefinitionFragment : Fragment(), DefAdapter.ClickListener {

    companion object {
        fun newInstance() = DefinitionFragment()
    }

    private lateinit var definitionViewModel: DefinitionViewModel
    private lateinit var adapter: DefAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_definition.layoutManager = layoutManager
        adapter = DefAdapter(context, this)
        rv_definition.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_definition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getRawData()
    }

    private fun initViewModel() {
        definitionViewModel = ViewModelProviders.of(this).get(DefinitionViewModel::class.java)
        definitionViewModel.let {
            lifecycle.addObserver(it)
        }
    }

    private fun getRawData() {
        definitionViewModel
                .getWordDefinition("example")?.observe(
                        this,
                        Observer { definitionList ->
                            adapter.updateInfo(definitionList!!)
                        })
    }

    override fun onThumbUp(position: Int) {
    }

    override fun onThumbUpDown(position: Int) {

    }
}
