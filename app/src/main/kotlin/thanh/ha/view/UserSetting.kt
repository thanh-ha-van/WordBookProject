package thanh.ha.view

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * @author Thanh
 * @date 3/1/2020
 */


class UserSetting @Inject constructor(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var isNightModeOn
        get() = run {
            sharedPref.getBoolean(NIGHT_MODE, false)
        }
        set(isOn) = run {
            sharedPref.edit().putBoolean(NIGHT_MODE, isOn).apply()
        }

    var isNotificationOn
        get() = run {
            sharedPref.getBoolean(NOTIFY_ALLOW, false)
        }
        set(isOn) = run {
            sharedPref.edit().putBoolean(NOTIFY_ALLOW, isOn).apply()
        }

    companion object {
        const val PREF_NAME = "workbook.thanh.ha"
        const val NIGHT_MODE = "night_mode"
        const val NOTIFY_ALLOW = "notification_allow"
    }
}