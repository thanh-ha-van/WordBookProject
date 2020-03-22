package thanh.ha.view.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import thanh.ha.R
import thanh.ha.data.remote.WodResponse
import thanh.ha.view.WodActivity


internal class NotificationHelper(private val mContext: Context) {

    fun createNotification(def: WodResponse) {
        def.meaning?.replace("\n" ,"")
        def.example?.replace("\n" ,"")
        val intent = Intent(mContext, WodActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("word", def.word)
        intent.putExtra("definition", def.meaning)
        intent.putExtra("sample", def.example)
        val resultPendingIntent = PendingIntent
                .getActivity(mContext,
                        0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
        val mBuilder = NotificationCompat.Builder(mContext)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder.setContentTitle("Today word: " + def.word)
                .setContentText(def.meaning)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(def.meaning + "\nExample: " + def.example))
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        val mNotificationManager
                = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "MY_WORD_BOOK_PROJECT", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "10101"
    }

}