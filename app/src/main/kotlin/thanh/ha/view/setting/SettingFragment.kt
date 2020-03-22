package thanh.ha.view.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.*
import thanh.ha.BuildConfig
import thanh.ha.R
import thanh.ha.di.WordBookApp
import thanh.ha.view.notify.ACTION_DAILY_DEFINITION
import thanh.ha.view.notify.NotificationReceiver
import java.util.*


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
            rescheduleWorks(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        rescheduleWorks(notification_switch.isChecked)
    }

    private var alarmManager: AlarmManager? = null

    private fun rescheduleWorks(isCheck: Boolean) {

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.action = ACTION_DAILY_DEFINITION
        val pendingIntent = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (isCheck) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)

            //if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)

            alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager?

            alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    //AlarmManager.INTERVAL_DAY,
                    60000,
                    pendingIntent)
        } else {
            alarmManager?.cancel(pendingIntent)
        }
    }
}
