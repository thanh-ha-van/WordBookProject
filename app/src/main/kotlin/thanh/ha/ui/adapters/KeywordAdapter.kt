package thanh.ha.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_keyword.view.*
import thanh.ha.R
import thanh.ha.domain.Keyword


class KeywordAdapter(context: Context?, private val mClickListener: ClickListener?)
    : RecyclerView.Adapter<KeywordAdapter.MyViewHolder>() {

    private var mDefList: MutableList<Keyword> = ArrayList()
    private val mContext = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    fun updateInfo(newItems: MutableList<Keyword>) {
        mDefList = newItems
        notifyDataSetChanged()
    }


    fun clear() {
        mDefList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return MyViewHolder(
                inflater.inflate(R.layout.item_keyword,
                        parent,
                        false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_word!!.text = mDefList[position].word
        holder.itemView.tv_word.setTextColor(getRandomColorInt(mContext!!))
        holder.itemView.setOnClickListener {
            mClickListener?.onClickKeyWord(mDefList[position].word)
        }
    }

    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onClickKeyWord(string: String)
    }
}
