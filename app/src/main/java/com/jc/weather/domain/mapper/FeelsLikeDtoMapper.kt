package com.jc.weather.domain.mapper

import com.jc.weather.data.FeelsLikeDto
import com.jc.weather.domain.model.FeelsLikeDomainModel
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