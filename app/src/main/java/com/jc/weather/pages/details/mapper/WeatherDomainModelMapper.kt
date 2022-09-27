package com.jc.weather.pages.details.mapper

import com.jc.weather.open_weather_map.domain.model.HourlyDomainModel
import com.jc.weather.pages.details.model.CollapsedModel
import com.jc.weather.pages.details.model.ExpandedModel
import com.jc.weather.pages.details.model.WindModel
import com.jc.weather.time.TimestampProvider
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun mapToCollapsedModel(model: HourlyDomainModel): CollapsedModel = with(model) {
        return CollapsedModel(
            id = time,
            isExpanded = false,
            time = timestampProvider.toTime(time),
            iconUrl = weather.iconUrl,
            humidity = humidity,
            generalDescription = weather.mainDescription,
            temperature = temperature,
            details = ExpandedModel(
                feelsLike = feelsLike,
                pressure = pressure,
                dewPoint = dewPoint,
                uvIndex = uvIndex,
                clouds = clouds,
                visibility = visibility,
                rain = 0.0F, // TODO: Get rain data from elsewhere or remove it
                wind = WindModel(
                    speed = windSpeed,
                    deg = windDeg,
                    gust = windGust
                ),
                detailedDescription = weather.detailedDescription
            )
        )
    }
}
