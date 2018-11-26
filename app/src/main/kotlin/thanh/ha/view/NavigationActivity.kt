package thanh.ha.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import thanh.ha.R
import thanh.ha.view.search.DefinitionFragment


class NavigationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            replaceFragment(DefinitionFragment.newInstance())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

}
