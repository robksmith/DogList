package com.zengo.models.dagger2.modules

import android.app.Application
import com.zengo.helpers.BuildTypesHelper
import com.zengo.networking.ApiService
import com.zengo.views.DogListApplication
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule(private val app: DogListApplication) {

    private var ws : ApiService? = null

    @Provides
    @Singleton
    internal fun provideRetrofit(): ApiService
    {
        return getCustomerAppService(app)!!
    }

    private fun getCustomerAppService(application: Application): ApiService?
    {
        if ( ws == null)
        {
            val okHttpClientBuilder = OkHttpClient.Builder()

            okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)

            if (BuildTypesHelper.isDebug())
            {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY

                okHttpClientBuilder.addInterceptor(logging)
            }

            ws = Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl(DOG_SERVICE_BASE_URL).
                build().
                create(ApiService::class.java)
        }

        return ws
    }

    companion object {

        val DOG_SERVICE_BASE_URL = "https://dog.ceo/api/"
    }
}