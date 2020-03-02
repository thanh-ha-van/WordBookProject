package thanh.ha.view.cards

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.PagerAdapter
@Deprecated ("Not use anymore, have bug")
class CardFragmentPagerAdapter(private val fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT), CardAdapter {

    private val mFragments: ArrayList<CardFragment> = ArrayList()

    private var baseId: Long = 0
    override fun getCardViewAt(position: Int): CardView? {
        if (mFragments.isNullOrEmpty()) return null
        return mFragments[position].cardView
    }

    override val baseElevation: Float
        get() = 6f
    override val mCount: Int
        get() = mFragments.size

    override fun getCount(): Int {

        return mFragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemId(position: Int): Long {
        return baseId + position
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position)
        mFragments[position] = fragment as CardFragment
        return fragment
    }

    fun clear() {
        for (item in mFragments) {
            val transaction: FragmentTransaction = fm.beginTransaction()
            transaction.remove(item)
            transaction.commitAllowingStateLoss()
        }
        mFragments.clear()
        notifyDataSetChanged()
    }

    fun addCardFragment(fragment: CardFragment) {
        mFragments.add(fragment)
        notifyDataSetChanged()
    }
}