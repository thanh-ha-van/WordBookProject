package thanh.ha.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable
import thanh.ha.base.BaseFragment
import thanh.ha.databinding.FragmentHomeBinding
import thanh.ha.ui.adapters.KeywordAdapter
import thanh.ha.ui.adapters.MyItemTouchHelper
import thanh.ha.ui.adapters.SmallWordDefAdapter


class HomeFragment(private var keywordPasser: KeywordPasser) : BaseFragment(),
    SmallWordDefAdapter.ClickListener,
    KeywordAdapter.ClickListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private var mDisposable: Disposable? = null
    private lateinit var savedWordAdapter: SmallWordDefAdapter
    private lateinit var recentSearchAdapter: KeywordAdapter

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getLocalSaved()
        getLocalRecentKeyword()

    }

    private fun initView() {

        //set up for save definitions adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSavedDef.layoutManager = layoutManager

        savedWordAdapter = SmallWordDefAdapter(context, this)
        binding.rvSavedDef.layoutManager = layoutManager
        binding.rvSavedDef.adapter = savedWordAdapter

        val swipeHandler = object : MyItemTouchHelper(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.rvSavedDef.adapter as SmallWordDefAdapter
                mHomeViewModel.removeLocal(
                    adapter.getItemAt(viewHolder.adapterPosition)
                )
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvSavedDef)
        //set up for recent search keyword adapter
        val layoutManager2 = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)

        recentSearchAdapter = KeywordAdapter(context, this)
        binding.rvRecent.layoutManager = layoutManager2
        binding.rvRecent.adapter = recentSearchAdapter
        binding.rvRecent.setItemViewCacheSize(10)
        binding.tvClearSaved.setOnClickListener {
            binding.savedDefDesc.visibility = View.VISIBLE
            mHomeViewModel.deleteAllLocalWord()
        }

        binding.tvClearSearch.setOnClickListener {
            binding.tvRecentSearchDesc.visibility = View.VISIBLE
            mHomeViewModel.deleteAllKeyword()
        }
    }

    private fun getLocalRecentKeyword() {
        mHomeViewModel.getLocalKeyword()?.observe(
            viewLifecycleOwner,
            Observer {
                if (it.isNullOrEmpty()) {
                    binding.tvRecentSearchDesc.visibility = View.VISIBLE
                } else {
                    binding.tvRecentSearchDesc.visibility = View.GONE
                }
                recentSearchAdapter.updateInfo(it.toMutableList())
            }
        )
    }

    override fun onClickKeyWord(string: String) {
        keywordPasser.onPassKeyword(string)
    }

    private fun getLocalSaved() {
        mHomeViewModel.getLocalDefinitions()?.observe(
            viewLifecycleOwner
        ) {
            if (it.isNullOrEmpty()) {
                binding.savedDefDesc.visibility = View.VISIBLE
            } else {
                binding.savedDefDesc.visibility = View.GONE
            }
            savedWordAdapter.updateInfo(it.toMutableList())
        }
    }

    override fun onResume() {
        super.onResume()
        if (recentSearchAdapter.itemCount > 0)
            binding.rvRecent.smoothScrollToPosition(recentSearchAdapter.itemCount - 1)
        if (savedWordAdapter.itemCount > 0)
            binding.rvSavedDef.smoothScrollToPosition(0)
    }

    private fun initViewModel() {
        mHomeViewModel = ViewModelProvider(this)
            .get(HomeViewModel::class.java)
        mHomeViewModel
            .let {
                lifecycle.addObserver(it)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposable != null && mDisposable?.isDisposed!!) mDisposable?.dispose()
    }
}
