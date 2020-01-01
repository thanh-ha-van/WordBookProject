package thanh.ha.view.search

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class CardFragmentPagerAdapter(fm: FragmentManager?, baseElevation: Float)
    : FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), CardAdapter {

    private val mFragments: MutableList<CardFragment>
    private val mBaseElevation: Float


    override fun getCardViewAt(position: Int): CardView? {
        if(mFragments.isNullOrEmpty()) return null
        return mFragments[position].cardView
    }

    override val baseElevation: Float
        get() = 6f
    override val mCount: Int
        get() = mFragments.size

    override fun getCount(): Int {

        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)
        mFragments[position] = fragment as CardFragment
        return fragment
    }

    fun addCardFragment(fragment: CardFragment) {
        mFragments.add(fragment)
        notifyDataSetChanged()
    }

    init {
        mFragments = ArrayList()
        mBaseElevation = baseElevation
    }
}