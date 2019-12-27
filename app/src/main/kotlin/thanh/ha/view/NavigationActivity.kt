package thanh.ha.view

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import thanh.ha.R
import thanh.ha.ui.adapters.ViewPagerAdapter
import thanh.ha.view.home.HomeFragment
import thanh.ha.view.search.SearchFragment
import thanh.ha.view.setting.SettingFragment
import java.util.*

class NavigationActivity : AppCompatActivity(),
        ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private var fragments: MutableList<Fragment> = ArrayList()
    private var homeFragment: HomeFragment? = null
    private var searchFragment: SearchFragment? = null
    private var settingFragment: SettingFragment? = null
    private var prevMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        setupViewPager(viewpager)
        viewpager.offscreenPageLimit = 0
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home -> viewpager.currentItem = 0
            R.id.action_search -> viewpager.currentItem = 1
            R.id.action_setting -> viewpager.currentItem = 2
        }
        return false
    }

    override fun onPageScrollStateChanged(p0: Int) {
        // do nothing
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        // do nothing
    }

    override fun onPageSelected(p0: Int) {
        if (prevMenuItem != null) {
            prevMenuItem!!.isChecked = false
        } else {
            bottom_navigation.menu.getItem(0).isChecked = false
        }
        bottom_navigation.menu.getItem(p0).isChecked = true
        prevMenuItem = bottom_navigation.menu.getItem(p0)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        fragments = ArrayList()
        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        settingFragment = SettingFragment()
        fragments.add(homeFragment!!)
        fragments.add(searchFragment!!)
        fragments.add(settingFragment!!)
        val adapter = ViewPagerAdapter(supportFragmentManager, fragments)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(this)
        viewPager.currentItem = 1
    }
}
