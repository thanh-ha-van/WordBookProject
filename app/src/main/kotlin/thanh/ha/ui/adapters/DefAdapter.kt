package thanh.ha.ui.adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.SpanHelper
import thanh.ha.ui.customSpanable.SpannableClickAction
import kotlin.contracts.contract


class DefAdapter(context: Context?, private val mClickListener: ClickListener)
    : RecyclerView.Adapter<DefAdapter.MyViewHolder>(), SpannableClickAction {

    private var mDefList: List<DefinitionInfo> = ArrayList()
    private val mContext = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mStar: ImageView? = null
        var mThumbUpBtn: ImageView? = null
        var mThumbDownBtn: ImageView? = null
        var mThumbUpValue: TextView? = null
        var mThumbDownValue: TextView? = null
        var mDefinition: TextView? = null
        var mExample: TextView? = null
        var mAuthor: TextView? = null
        var mTime: TextView? = null

        init {
            mStar = view.findViewById(R.id.img_star)
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
        mDefList = newItems
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return MyViewHolder(
                inflater.inflate(R.layout.item_word_definition,
                        parent,
                        false)
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mDefinition?.movementMethod = LinkMovementMethod.getInstance()
        holder.mExample?.movementMethod = LinkMovementMethod.getInstance()
        holder.mDefinition?.text =
                SpanHelper.appSpirit(mContext!!, mDefList[position].definition, this)
        holder.mExample!!.text =
                SpanHelper.appSpirit(mContext, mDefList[position].example, this)
        holder.mThumbUpValue!!.text = mDefList[position].thumbsUp.toString()
        holder.mThumbDownValue!!.text = mDefList[position].thumbsDown.toString()
        holder.mAuthor!!.text = mDefList[position].author
        holder.mTime!!.text = mDefList[position].writtenOn
        if (position == 0) {
            holder.mStar!!.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onThumbUp(position: Int)
        fun onThumbUpDown(position: Int)
        fun onClickKeyWord(string: String)
    }

    override fun onClick(string: String) {
        mClickListener.onClickKeyWord(string)
    }
}
