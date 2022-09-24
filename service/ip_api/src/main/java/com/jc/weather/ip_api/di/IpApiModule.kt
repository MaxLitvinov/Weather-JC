package com.jc.weather.ip_api.di

import com.jc.weather.ip_api.BuildConfig
import com.jc.weather.ip_api.data.api.IpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class IpApiModule {

    @Provides
    fun provideIpApi(retrofitBuilder: Retrofit.Builder): IpApi =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_IP_API)
            .build()
            .create(IpApi::class.java)
}