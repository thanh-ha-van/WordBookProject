package thanh.ha.ui.adapters

import android.content.Context
import android.text.method.LinkMovementMethod
import androidx.recyclerview.widget.RecyclerView
import thanh.ha.databinding.ItemWordDefinitionBinding
import thanh.ha.domain.DefinitionInfo
import thanh.ha.helpers.SpanHelper
import thanh.ha.ui.customSpanable.SpannableClickAction

class DefViewHolder(
    private var binding: ItemWordDefinitionBinding,
    private var context: Context,
    private val mClickListener: DefAdapter.ClickListener?
) : RecyclerView.ViewHolder(binding.root), SpannableClickAction {

    fun bind(item: DefinitionInfo) {
        with(binding) {
            tvDefinition.movementMethod = LinkMovementMethod.getInstance()
           tvExample.movementMethod = LinkMovementMethod.getInstance()

            // map data
           tvDefinition.text =
                SpanHelper.appSpirit(context, item.definition, this@DefViewHolder)

           tvExample.text =
                SpanHelper.appSpirit(context, item.example, this@DefViewHolder)

           tvThumbUpValue.text = item.thumbsUp.toString()
           tvThumbDownValue.text = item.thumbsDown.toString()

           tvAuthor.text = item.author
           tvTime.text = item.writtenOn
           tvWord.text = item.word
           likeIcon.isChecked = false

           likeIcon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    mClickListener?.onSaveClicked(item)
                } else {
                    mClickListener?.onUnSaveClicked(item)
                }
            }
        }
    }

    override fun onClick(string: String) {
        mClickListener?.onClickKeyWord(string)
    }

}