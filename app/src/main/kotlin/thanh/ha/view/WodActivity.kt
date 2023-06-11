package thanh.ha.view

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import thanh.ha.databinding.ActivityWodBinding

class WodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        processIntent(savedInstanceState)
    }

    private fun processIntent(bundle: Bundle?) {
        if (bundle != null) {

            val word = bundle.getString("word")
            val definition = bundle.getString("definition")
            val sample = bundle.getString("sample")

            binding.tvDefinition.movementMethod = LinkMovementMethod.getInstance()
            binding.tvExample.movementMethod = LinkMovementMethod.getInstance()

            binding.tvWord.text = word
            binding.tvDefinition.text = definition
            binding.tvExample.text = sample

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
