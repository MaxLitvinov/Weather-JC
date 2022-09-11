package com.jc.weather.di

import com.jc.weather.BuildConfig
import com.jc.weather.data.ip_api.IpApi
import com.jc.weather.data.open_weather_map.OpenWeatherMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {

        private const val REQUEST_TIMEOUT_SECONDS = 15L
    }

    @Provides
    fun provideWeatherApi(): OpenWeatherMapApi =
        createApi(BuildConfig.BASE_URL_OPEN_WEATHER_MAP, OpenWeatherMapApi::class.java)

    @Provides
    fun provideIpApi(): IpApi =
        createApi(BuildConfig.BASE_URL_IP_API, IpApi::class.java)

    private fun <API> createApi(baseUrl: String, apiClass: Class<API>): API =
        Retrofit.Builder()
            .client(createOkHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiClass)

    private fun createOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
}
