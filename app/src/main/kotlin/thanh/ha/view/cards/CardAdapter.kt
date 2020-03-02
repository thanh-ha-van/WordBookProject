package thanh.ha.view.cards

import androidx.cardview.widget.CardView

@Deprecated ("Not use anymore, have bug")
interface CardAdapter {

    val baseElevation: Float

    fun getCardViewAt(position: Int): CardView?

    val mCount: Int

    companion object {
        const val MAX_ELEVATION_FACTOR = 4
    }
}