package com.jc.weather.open_weather_map.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.jc.weather.open_weather_map.BuildConfig
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherDataStoreRepository @Inject constructor(
    private val gson: Gson,
    private val dataStore: DataStore<Preferences>
) {

    companion object {

        private val PREFERENCES_KEY_NAME = "weather_data_preferences_name${getSuffix()}"
        private val WEATHER_DATA_PREFS_KEY = stringPreferencesKey(PREFERENCES_KEY_NAME)

        private fun getSuffix(): String = when (BuildConfig.DEBUG) {
            true -> "_${BuildConfig.BUILD_TYPE}"
            false -> ""
        }
    }

    suspend fun saveData(model: WeatherDomainModel) {
        val json: String = mapToJson(model)
        dataStore.edit { settings ->
            settings[WEATHER_DATA_PREFS_KEY] = json
        }
    }

    private fun mapToJson(model: WeatherDomainModel): String =
        gson.toJson(model, WeatherDomainModel::class.java)

    suspend fun getData(): WeatherDomainModel? {
        val preferences = dataStore.data.first()
        preferences[WEATHER_DATA_PREFS_KEY]?.let { json ->
            return mapToDomainModel(json)
        }
        return null
    }

    private fun mapToDomainModel(json: String): WeatherDomainModel =
        gson.fromJson(json, WeatherDomainModel::class.java)
}