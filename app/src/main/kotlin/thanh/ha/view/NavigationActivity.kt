package thanh.ha.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import thanh.ha.R


class NavigationActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(DefinitionFragment.newInstance())
        }

        initNavigation()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(DefinitionFragment.newInstance())
                return true
            }
//      R.id.navigation_dashboard -> {
//        replaceFragment(AboutFragment.newInstance())
//        return true
//      }
        }
        return false
    }

    private fun initNavigation() {
        navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

}
