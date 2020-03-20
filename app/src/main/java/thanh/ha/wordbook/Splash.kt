package thanh.ha.wordbook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import thanh.ha.R
import thanh.ha.view.NavigationActivity

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        startAnimation()
        Handler().postDelayed(
                {
                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }, 2000
        )
    }

    private fun startAnimation() {

    }
}
