package thanh.ha.view.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_DAILY_DEFINITION) {
            MyIntentService.getDailyDefinition(context, intent)
        }
    }
}