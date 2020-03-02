package thanh.ha.ui.adapters


import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_small_word_definition.view.*
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.SpanHelper
import thanh.ha.ui.customSpanable.SpannableClickAction


class SmallWordDefAdapter(context: Context?, private val mClickListener: ClickListener?)
    : RecyclerView.Adapter<SmallWordDefAdapter.MyViewHolder>(), SpannableClickAction {

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
                inflater.inflate(R.layout.item_small_word_definition,
                        parent,
                        false)
        )
    }

    fun getItemAt(position: Int) : DefinitionInfo {
        return mDefList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // to override the onclick of text view
        holder.itemView.tv_definition.movementMethod = LinkMovementMethod.getInstance()
        holder.itemView.tv_sample.movementMethod = LinkMovementMethod.getInstance()
        // map data
        holder.itemView.tv_definition?.text =
                SpanHelper.appSpirit(mContext!!, mDefList[position].definition, this)
        holder.itemView.tv_sample!!.text =
                SpanHelper.appSpirit(mContext, mDefList[position].example, this)
        holder.itemView.tv_word!!.text = mDefList[position].word

        //val isExpanded = position == mExpandedPosition
        //holder.itemView.tv_sample.visibility = if (isExpanded) View.VISIBLE else View.GONE
        //holder.itemView.isActivated = isExpanded
        //if (isExpanded)
        //    previousExpandedPosition = position

//        holder.itemView.tv_word.setOnClickListener {
//            mExpandedPosition = if (isExpanded) -1 else position
//            notifyItemChanged(previousExpandedPosition)
//            notifyItemChanged(position)
//        }
//        holder.itemView.tv_definition.setOnClickListener {
//            mExpandedPosition = if (isExpanded) -1 else position
//            notifyItemChanged(previousExpandedPosition)
//            notifyItemChanged(position)
//        }
//        holder.itemView.tv_sample.setOnClickListener {
//            mExpandedPosition = if (isExpanded) -1 else position
//            notifyItemChanged(previousExpandedPosition)
//            notifyItemChanged(position)
//        }
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
