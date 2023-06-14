package thanh.ha.ui.adapters


import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.SpanHelper
import thanh.ha.ui.customSpanable.SpannableClickAction


class SmallWordDefAdapter(context: Context?, private val mClickListener: ClickListener?) :
    RecyclerView.Adapter<SmallWordDefAdapter.MyViewHolder>(), SpannableClickAction {

    private var mDefList: MutableList<DefinitionInfo> = ArrayList()
    private val mContext = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun updateInfo(newItems: MutableList<DefinitionInfo>) {
        mDefList = newItems
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        mDefList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        mDefList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return MyViewHolder(
            inflater.inflate(
                R.layout.item_small_word_definition,
                parent,
                false
            )
        )
    }

    fun getItemAt(position: Int): DefinitionInfo {
        return mDefList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val definition = holder.itemView.findViewById<TextView>(R.id.tv_definition)
        val sample = holder.itemView.findViewById<TextView>(R.id.tv_sample)
        val word = holder.itemView.findViewById<TextView>(R.id.tv_word)
        // to override the onclick of text view
        definition.movementMethod = LinkMovementMethod.getInstance()
        sample.movementMethod = LinkMovementMethod.getInstance()
        // map data
        definition?.text =
            SpanHelper.appSpirit(mContext!!, mDefList[position].definition, this)
        sample!!.text =
            SpanHelper.appSpirit(mContext, mDefList[position].example, this)
        word!!.text = mDefList[position].word

    }


    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onClickKeyWord(string: String)
    }

    override fun onClick(string: String) {
        mClickListener?.onClickKeyWord(string)
    }
}
