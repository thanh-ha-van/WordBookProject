package thanh.ha.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import thanh.ha.R

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }
}
