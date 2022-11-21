package com.jc.weather.open_weather_map.domain.mapper

import com.jc.weather.open_weather_map.data.dto.DailyDto
import com.jc.weather.open_weather_map.data.dto.HourlyDto
import com.jc.weather.open_weather_map.data.dto.WeatherDto
import com.jc.weather.open_weather_map.domain.model.DailyDomainModel
import com.jc.weather.open_weather_map.domain.model.HourlyDomainModel
import com.jc.weather.open_weather_map.domain.model.WeatherDomainModel
import com.jc.weather.time.TimestampProvider
import javax.inject.Inject

class WeatherDtoMapper @Inject constructor(
    private val currentDtoMapper: CurrentDtoMapper,
    private val hourlyDtoMapper: HourlyDtoMapper,
    private val dailyDtoMapper: DailyDtoMapper,
    private val timestampProvider: TimestampProvider
) {

    fun mapToDomainModel(
        dto: WeatherDto
    ) = with(dto) {
        WeatherDomainModel(
            latitude = latitude,
            longitude = longitude,
            timezone = timezone,
            timezoneOffset = timezoneOffset,
            currentWeather = currentDtoMapper.mapToDomainModel(current),
            dailyForecasts = getDailyForecasts(timezoneOffset, dailyForecasts, hourlyForecasts)
        )
    }

    // TODO: Fix getDailyForecasts
    private fun getDailyForecasts(
        timezoneOffset: Int,
        dailyForecastDtoList: List<DailyDto>,
        hourlyForecastDtoList: List<HourlyDto>?
    ): List<DailyDomainModel> {
        val hourlyMap = getHourlyMap(timezoneOffset, hourlyForecastDtoList)

        return dailyForecastDtoList.map { dailyDto ->
            val dailyForecastDomain = dailyDtoMapper.mapToDomainModel(timezoneOffset, dailyDto)
            val time = transformDate(dailyForecastDomain.time)
            if (hourlyMap?.containsKey(time) == true) {
                dailyForecastDomain.copy(hourlyForecasts = hourlyMap[time])
            } else {
                dailyForecastDomain
            }
        }
    }

    private fun getHourlyMap(
        timezoneOffset: Int,
        hourlyForecastDtoList: List<HourlyDto>?
    ): Map<String, MutableList<HourlyDomainModel>>? {
        if (hourlyForecastDtoList.isNullOrEmpty()) {
            return null
        }
        val hourlyMap = HashMap<String, MutableList<HourlyDomainModel>>()

        hourlyForecastDtoList.map { hourlyDto ->
            val hourlyForecastDomain = hourlyDtoMapper.mapToDomainModel(timezoneOffset, hourlyDto)

            val date = transformDate(hourlyForecastDomain.time)
            when (hourlyMap.containsKey(date)) {
                true -> hourlyMap[date]?.add(hourlyForecastDomain)
                false -> hourlyMap[date] = mutableListOf(hourlyForecastDomain)
            }

            hourlyForecastDomain
        }
        return hourlyMap
    }

    private fun transformDate(dateTime: Long) = timestampProvider.toYearMonthDay(dateTime)
}
