package thanh.ha.view

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wod.*
import thanh.ha.R

class WodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wod)
        processIntent(savedInstanceState)
    }

    private fun processIntent(bundle: Bundle?) {
        if (bundle != null) {

            val word = bundle.getString("word")
            val definition = bundle.getString("definition")
            val sample = bundle.getString("sample")

            tv_definition.movementMethod = LinkMovementMethod.getInstance()
            tv_example.movementMethod = LinkMovementMethod.getInstance()

            tv_word.text = word
            tv_definition.text = definition
            tv_example!!.text = sample

        } else {
            Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
