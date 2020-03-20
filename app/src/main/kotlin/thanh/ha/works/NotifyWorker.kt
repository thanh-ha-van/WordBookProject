package thanh.ha.works

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import thanh.ha.R
import thanh.ha.view.NavigationActivity
import java.util.concurrent.TimeUnit


class NotifyWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        getTheWorldOfTheDay()
        sendNotification("Title")
        return Result.success()
    }

    private fun getTheWorldOfTheDay(): String {
        return "Dummy"
    }

    fun scheduleReminder(duration: Long) {

        val notificationWork = OneTimeWorkRequest
                .Builder(NotifyWorker::class.java)
                .setInitialDelay(duration, TimeUnit.DAYS)
                .addTag("thanh.ha.workbook")
                .build()

        WorkManager
                .getInstance(context)
                .enqueue(notificationWork)
    }

    fun cancelReminder() {
        val instance = WorkManager.getInstance(context)
        instance.cancelAllWorkByTag("thanh.ha.workbook")
    }

    private fun sendNotification(title: String) {

        val intent = Intent(applicationContext, NavigationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("notification_id", id)

        val pendingIntent =
                PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("thanh.ha.workbook",
                    "Thanh.Ha.WB",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat
                .Builder(applicationContext, "thanh.ha.workbook")
                .setContentTitle(title)
                .setContentText(title)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_noun)
                .setAutoCancel(true)
        notificationManager.notify(311, notification.build())
    }
}