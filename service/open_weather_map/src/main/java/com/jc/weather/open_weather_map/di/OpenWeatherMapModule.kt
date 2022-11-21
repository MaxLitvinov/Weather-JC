package com.jc.weather.open_weather_map.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.jc.weather.open_weather_map.BuildConfig
import com.jc.weather.open_weather_map.data.api.OpenWeatherMapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OpenWeatherMapModule {

    companion object {

        private const val SHARED_PREFERENCES_NAME = "weather_app_shared_preferences_name"
    }

    @Provides
    fun provideWeatherApi(retrofitBuilder: Retrofit.Builder): OpenWeatherMapApi =
        retrofitBuilder
            .baseUrl(BuildConfig.BASE_URL_OPEN_WEATHER_MAP)
            .build()
            .create(OpenWeatherMapApi::class.java)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
            migrations = listOf(
                SharedPreferencesMigration(
                    context = context,
                    sharedPreferencesName = SHARED_PREFERENCES_NAME
                )
            ),
            scope = CoroutineScope(context = Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(SHARED_PREFERENCES_NAME) }
        )
}
