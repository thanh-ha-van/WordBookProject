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
import thanh.ha.BuildConfig
import thanh.ha.R
import thanh.ha.databinding.FragmentSettingBinding
import thanh.ha.di.WordBookApp
import thanh.ha.view.notify.ACTION_DAILY_DEFINITION
import thanh.ha.view.notify.NotificationReceiver
import java.util.*


class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvVersion.text = getString(R.string.setting_version, BuildConfig.VERSION_NAME)

        binding.nightModeSwitch.isChecked = WordBookApp.appSetting.isNightModeOn

        binding.nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            WordBookApp.appSetting.isNightModeOn = isChecked
        }
        binding.notificationSwitch.isChecked = WordBookApp.appSetting.isNotificationOn

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            WordBookApp.appSetting.isNotificationOn = isChecked
            rescheduleWorks(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        rescheduleWorks(binding.notificationSwitch.isChecked)
    }

    private var alarmManager: AlarmManager? = null

    private fun rescheduleWorks(isCheck: Boolean) {

        val intent = Intent(context, NotificationReceiver::class.java)
        intent.action = ACTION_DAILY_DEFINITION
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (isCheck) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 8)
            calendar.set(Calendar.MINUTE, 0)

            //if (calendar.time < Date()) calendar.add(Calendar.DAY_OF_MONTH, 1)

            alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager?

            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                //AlarmManager.INTERVAL_DAY,
                60000,
                pendingIntent
            )
        } else {
            alarmManager?.cancel(pendingIntent)
        }
    }
}
