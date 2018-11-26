package thanh.ha.ui.adapters


import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import thanh.ha.R
import thanh.ha.domain.DefinitionInfo
import thanh.ha.ui.customTag.AndroidTagBadgeBuilder
import thanh.ha.ui.customTag.BadgeListViewModel
import thanh.ha.ui.customTag.SpannableClickAction
import thanh.ha.ui.customTag.TagDto
import java.util.*


class DefAdapter(context: Context?, private val mClickListener: ClickListener)
    : RecyclerView.Adapter<DefItemViewHolder>() {
    private val TEXT_COLOR = "#ffffff"
    private var mDefList: List<DefinitionInfo> = ArrayList()
    private val mContext = context
    private var viewModel: BadgeListViewModel? = null
    val badgeBuilder = AndroidTagBadgeBuilder(SpannableStringBuilder(), 2, TEXT_COLOR)


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefItemViewHolder {
        val inflater = LayoutInflater.from(mContext)
        return DefItemViewHolder(inflater.inflate(R.layout.item_word_definition, parent, false), mClickListener)

    }

    override fun onBindViewHolder(holder: DefItemViewHolder, position: Int) {

        holder.mExample!!.text = mDefList[position].example
        holder.mThumbUpValue!!.text = mDefList[position].thumbsUp.toString()
        holder.mThumbDownValue!!.text = mDefList[position].thumbsDown.toString()
        holder.mAuthor!!.text = mDefList[position].author
        holder.mTime!!.text = mDefList[position].writtenOn
        if (position == 0) {
            holder.mStar!!.visibility = View.VISIBLE
        }
        viewModel = BadgeListViewModel(badgeBuilder, getDefaultClickAction())
        viewModel?.setTags(dummyTagData)
        holder.mDefinition!!.text = viewModel?.renderedTagBadges
    }

    private fun getDefaultClickAction(): SpannableClickAction {

        return SpannableClickAction {

            fun onClick() {
                Toast.makeText(this.mContext, "", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val dummyTagData = Arrays.asList(
            TagDto.createWith("#Dummy1", "#3BC23F"),
            TagDto.createWith("#Dummy2", "#29BDCC"),
            TagDto.createWith("#Dummy3", "#6B255E"),
            TagDto.createWith("#Dummy4", "#382DB5"),
            TagDto.createWith("#Dummy5", "#ff2c2a"),
            TagDto.createWith("#Dummy6", "#6B255E"),
            TagDto.createWith("#Dummy7", "#29BDCC"),
            TagDto.createWith("#Dummy8", "#29BDCC"),
            TagDto.createWith("#ExtraLargeDummyFTW", "#382DB5"),
            TagDto.createWith("#Dummy9", "#3BC23F"),
            TagDto.createWith("#Dummy10", "#6B255E")
    )

    override fun getItemCount(): Int {
        return mDefList.size
    }

    interface ClickListener {
        fun onThumbUp(position: Int)
        fun onThumbUpDown(position: Int)

    }
}
