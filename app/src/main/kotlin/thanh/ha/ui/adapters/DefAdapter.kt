package thanh.ha.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thanh.ha.databinding.ItemWordDefinitionBinding
import thanh.ha.domain.DefinitionInfo
import thanh.ha.ui.customSpanable.SpannableClickAction


class DefAdapter(private var context: Context, private val mClickListener: ClickListener?) :
    RecyclerView.Adapter<DefViewHolder>(), SpannableClickAction {

    private var mDefList: List<DefinitionInfo> = ArrayList()

    private lateinit var binding: ItemWordDefinitionBinding
    fun updateInfo(newItems: List<DefinitionInfo>) {
        mDefList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefViewHolder {
        binding = ItemWordDefinitionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DefViewHolder(binding, context, mClickListener)
    }

    override fun onBindViewHolder(holder: DefViewHolder, position: Int) {

        // to override the onclick of text view
        val item = mDefList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onSaveClicked(item: DefinitionInfo)
        fun onUnSaveClicked(item: DefinitionInfo)
        fun onClickKeyWord(string: String)
    }

    override fun onClick(string: String) {
        mClickListener?.onClickKeyWord(string)
    }
}
