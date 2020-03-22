package thanh.ha.view.notify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thanh.ha.data.remote.RemoteService
import thanh.ha.utils.isRunning


const val ACTION_DAILY_DEFINITION = "thanh.ha.wordbook.action.DAILY"

class MyIntentService : JobIntentService() {

    fun enqueueWork(context: Context, work: Intent) {
        enqueueWork(context, MyIntentService::class.java, 101, work)
    }

    override fun onHandleWork(intent: Intent) {
        if (!isRunning(applicationContext))
            initRetrofit()
    }

    @SuppressLint("CheckResult")
    private fun initRetrofit() {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        val retrofit =
                Retrofit.Builder()
                        .baseUrl("http://urban-word-of-the-day.herokuapp.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient)
                        .build()
        val service =
                retrofit.create(RemoteService::class.java)
        service.getWordOfDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { currencyResponse ->
                    NotificationHelper(applicationContext).createNotification(
                            currencyResponse
                    )
                }

    }

    companion object {

        @JvmStatic
        fun getDailyDefinition(context: Context?, intent: Intent?) {
            if (context != null && intent != null)
                MyIntentService().enqueueWork(context, intent)
        }

    }
}
