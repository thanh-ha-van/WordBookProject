package thanh.ha.view.cards

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager.widget.ViewPager.PageTransformer
@Deprecated ("Not use anymore")
class ShadowTransformer(private val mViewPager: ViewPager, adapter: CardAdapter) : OnPageChangeListener, PageTransformer {

    private val mAdapter: CardAdapter
    private var mLastOffset = 0f
    private var mScalingEnabled = false
    private val scale = 0.1f

    fun enableScaling(enable: Boolean) {
        mScalingEnabled = enable
        if (mScalingEnabled && !enable) {
            val currentCard = mAdapter.getCardViewAt(mViewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1f)
                currentCard.animate().scaleX(1f)
                currentCard.invalidate()
            }
        } else if (!mScalingEnabled && enable) {
            val currentCard = mAdapter.getCardViewAt(mViewPager.currentItem)
            if (currentCard != null) {
                currentCard.animate().scaleY(1 + scale)
                currentCard.animate().scaleX(1 + scale)
                currentCard.invalidate()
            }
        }
    }

    override fun transformPage(page: View, position: Float) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        val realCurrentPosition: Int
        val nextPosition: Int
        val baseElevation = mAdapter.baseElevation
        val realOffset: Float
        val goingLeft = mLastOffset > positionOffset
        if (goingLeft) {
            realCurrentPosition = position + 1
            nextPosition = position
            realOffset = 1 - positionOffset
        } else {
            nextPosition = position + 1
            realCurrentPosition = position
            realOffset = positionOffset
        }
        if (nextPosition > mAdapter.mCount - 1
                || realCurrentPosition > mAdapter.mCount - 1) {
            return
        }
        val currentCard = mAdapter.getCardViewAt(realCurrentPosition)

        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.scaleX = (1 + scale * (1 - realOffset))
                currentCard.scaleY = (1 + scale * (1 - realOffset))
            }
            currentCard.cardElevation = baseElevation + (baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset))
        }
        val nextCard = mAdapter.getCardViewAt(nextPosition)
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.scaleX = (1 + scale * realOffset)
                nextCard.scaleY = (1 + scale * realOffset)
            }
            nextCard.cardElevation = baseElevation + (baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * realOffset)
        }
        mLastOffset = positionOffset
    }

    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}

    init {
        mViewPager.addOnPageChangeListener(this)
        mAdapter = adapter
    }
}