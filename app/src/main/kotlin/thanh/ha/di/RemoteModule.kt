package thanh.ha.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thanh.ha.data.remote.RemoteContract
import thanh.ha.data.remote.RemoteService
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder()
                    .setLenient()
                    .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(RemoteContract.BASE_API)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()


    @Provides
    @Singleton
    fun provideRemoteCurrencyService(retrofit: Retrofit): RemoteService =
            retrofit.create(RemoteService::class.java)

}
