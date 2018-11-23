package thanh.ha.ui


import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo


class DefAdapter(context: Context?, private val mClickListener: ClickListener)
    : RecyclerView.Adapter<DefAdapter.MyViewHolder>() {

    private var mDefList: List<DefinitionInfo> = ArrayList()
    private val mContext = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mThumbUpBtn: ImageView? = null
        var mThumbDownBtn: ImageView? = null

        var mThumbUpValue: TextView? = null
        var mThumbDownValue: TextView? = null

        var mDefinition: TextView? = null
        var mExample: TextView? = null
        var mAuthor: TextView? = null
        var mTime: TextView? = null

        init {
            mThumbUpBtn = view.findViewById(R.id.btn_up)
            mThumbDownBtn = view.findViewById(R.id.btn_down)
            mThumbUpValue = view.findViewById(R.id.tv_thumb_up_value)
            mThumbDownValue = view.findViewById(R.id.tv_thumb_down_value)
            mDefinition = view.findViewById(R.id.tv_definition)
            mExample = view.findViewById(R.id.tv_example)
            mAuthor = view.findViewById(R.id.tv_author)
            mTime = view.findViewById(R.id.tv_time)
            mThumbUpBtn!!.setOnClickListener { mClickListener.onThumbUp(adapterPosition) }
            mThumbDownBtn!!.setOnClickListener { mClickListener.onThumbUpDown(adapterPosition) }
        }
    }

    fun updateInfo(newItems: List<DefinitionInfo>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return mDefList.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return mDefList[oldItemPosition].defId == newItems[newItemPosition].defId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val newWeather = newItems[newItemPosition]
                val oldWeather = mDefList[oldItemPosition]
                return newWeather.defId == oldWeather.defId
            }
        })
        mDefList = newItems
        result.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return MyViewHolder(inflater.inflate(R.layout.item_word_definition, parent, false))

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mDefinition!!.text = mDefList[position].definition
        holder.mExample!!.text = mDefList[position].example
        holder.mThumbUpValue!!.text = mDefList[position].thumbsUp.toString()
        holder.mThumbDownValue!!.text = mDefList[position].thumbsDown.toString()
        holder.mAuthor!!.text = mDefList[position].author
        holder.mTime!!.text = mDefList[position].writtenOn
    }

    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onThumbUp(position: Int)
        fun onThumbUpDown(position: Int)

    }
}
