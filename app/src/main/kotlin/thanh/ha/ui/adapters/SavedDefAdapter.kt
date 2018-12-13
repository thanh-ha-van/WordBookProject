package thanh.ha.ui.adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_saved_def.view.*
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.SpanHelper
import thanh.ha.ui.customSpanable.SpannableClickAction


class SavedDefAdapter(context: Context?, private val mClickListener: ClickListener?)
    : RecyclerView.Adapter<SavedDefAdapter.MyViewHolder>(), SpannableClickAction {

    private var mDefList: List<DefinitionInfo> = ArrayList()
    private val mContext = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun updateInfo(newItems: List<DefinitionInfo>) {
        mDefList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return MyViewHolder(
                inflater.inflate(R.layout.item_saved_def,
                        parent,
                        false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // to override the onclick of text view
        holder.itemView.tv_definition.movementMethod = LinkMovementMethod.getInstance()
        holder.itemView.tv_example.movementMethod = LinkMovementMethod.getInstance()

        // map data
        holder.itemView.tv_definition?.text =
                SpanHelper.appSpirit(mContext!!, mDefList[position].definition, this)

        holder.itemView.tv_example!!.text =
                SpanHelper.appSpirit(mContext, mDefList[position].example, this)

        holder.itemView.tv_thumb_up_value!!.text = mDefList[position].thumbsUp.toString()
        holder.itemView.tv_thumb_down_value!!.text = mDefList[position].thumbsDown.toString()
        holder.itemView.tv_author!!.text = mDefList[position].author
        holder.itemView.tv_time!!.text = mDefList[position].writtenOn
        holder.itemView.tv_word!!.text = mDefList[position].word

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
