package thanh.ha.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import thanh.ha.R


class DefItemViewHolder(view: View, clickListener: DefAdapter.ClickListener) : RecyclerView.ViewHolder(view) {

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
        mThumbUpBtn!!.setOnClickListener { clickListener.onThumbUp(adapterPosition) }
        mThumbDownBtn!!.setOnClickListener { clickListener.onThumbUpDown(adapterPosition) }
    }
}