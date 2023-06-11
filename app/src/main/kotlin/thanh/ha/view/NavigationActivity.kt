package thanh.ha.view

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import thanh.ha.R
import thanh.ha.databinding.ActivityMainBinding
import thanh.ha.ui.adapters.ViewPagerAdapter
import thanh.ha.view.home.HomeFragment
import thanh.ha.view.home.KeywordPasser
import thanh.ha.view.search.SearchFragment
import thanh.ha.view.setting.SettingFragment
import java.util.*

class NavigationActivity : AppCompatActivity(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener,
    KeywordPasser {

    private var fragments: MutableList<Fragment> = ArrayList()
    private var homeFragment: HomeFragment? = null
    private var searchFragment: SearchFragment? = null
    private var settingFragment: SettingFragment? = null
    private var prevMenuItem: MenuItem? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        with(binding.viewpager) {
            setupViewPager(this)
            setSwipeable(false)
            offscreenPageLimit = 0
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean = with(binding.viewpager) {
        when (p0.itemId) {
            R.id.action_home -> currentItem = 0
            R.id.action_search -> currentItem = 1
            R.id.action_setting -> currentItem = 2
        }
        return false
    }

    override fun onPageScrollStateChanged(p0: Int) {
        // do nothing
    }

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
        // do nothing
    }

    override fun onPageSelected(p0: Int) = with(binding.bottomNavigation) {
        if (prevMenuItem != null) {
            prevMenuItem!!.isChecked = false
        } else {
            menu.getItem(0).isChecked = false
        }
        menu.getItem(p0).isChecked = true
        prevMenuItem = menu.getItem(p0)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        fragments = ArrayList()
        homeFragment = HomeFragment(this)
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

    override fun onPassKeyword(string: String) {
        binding.viewpager.currentItem = 1
        searchFragment?.onSearchIntent(string)
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
