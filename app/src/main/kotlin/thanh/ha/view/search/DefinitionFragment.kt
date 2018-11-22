package thanh.ha.view.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import thanh.ha.R


class DefinitionFragment : Fragment() {

    companion object {
        fun newInstance() = DefinitionFragment()
    }

    private val definitions = ArrayList<String>()

    private lateinit var tv: TextView

    private lateinit var definitionViewModel: DefinitionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_definition, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv = view.findViewById(R.id.example_tv)
        getRawData()
    }

    private fun initViewModel() {
        definitionViewModel = ViewModelProviders.of(this).get(DefinitionViewModel::class.java)
        definitionViewModel.let { lifecycle.addObserver(it) }
    }


    private fun getRawData() {
        definitionViewModel.getWordDefinition("example")?.observe(this, Observer { definitionList ->
            definitionList?.forEach {
                definitions.add(it.info)

            }
            tv.text = definitions[1]
        })
    }
}
