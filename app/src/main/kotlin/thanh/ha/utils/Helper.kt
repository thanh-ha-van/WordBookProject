package thanh.ha.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context

@SuppressLint("NewApi")
fun isRunning(ctx: Context): Boolean {
    val activityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val tasks = activityManager.getRunningTasks(Int.MAX_VALUE)
    for (task in tasks) {
        if (ctx.packageName.equals(task.baseActivity!!.packageName, ignoreCase = true)) return true
    }
    return false
}