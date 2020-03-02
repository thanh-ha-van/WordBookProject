package thanh.ha.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.*
import thanh.ha.BuildConfig
import thanh.ha.R
import thanh.ha.di.WordBookApp

class SettingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_version.text = getString(R.string.setting_version, BuildConfig.VERSION_NAME)

        night_mode_switch.isChecked = WordBookApp.appSetting.isNightModeOn

        night_mode_switch.setOnCheckedChangeListener { _, isChecked ->
            WordBookApp.appSetting.isNightModeOn = isChecked
        }
        notification_switch.isChecked = WordBookApp.appSetting.isNotificationOn
        notification_switch.setOnCheckedChangeListener { _, isChecked ->
            WordBookApp.appSetting.isNotificationOn = isChecked
        }
    }
}
