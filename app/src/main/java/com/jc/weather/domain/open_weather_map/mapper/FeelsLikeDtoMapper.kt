package com.jc.weather.domain.open_weather_map.mapper

import com.jc.weather.data.open_weather_map.FeelsLikeDto
import com.jc.weather.domain.open_weather_map.model.FeelsLikeDomainModel
import javax.inject.Inject

class FeelsLikeDtoMapper @Inject constructor() {

    fun mapToDomainModel(dto: FeelsLikeDto) = with(dto) {
        FeelsLikeDomainModel(
            day = day,
            night = night,
            evening = evening,
            morning = morning
        )
    }
}
