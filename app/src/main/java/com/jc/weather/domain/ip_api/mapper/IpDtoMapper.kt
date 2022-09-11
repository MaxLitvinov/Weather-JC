package com.jc.weather.domain.ip_api.mapper

import com.jc.weather.data.ip_api.IpDto
import com.jc.weather.domain.ip_api.model.IpDomainModel
import javax.inject.Inject

class IpDtoMapper @Inject constructor() {

    fun mapToDomain(dto: IpDto) = with(dto) {

        IpDomainModel(
            query = query,
            status = status,
            country = country,
            countryCode = countryCode,
            region = region,
            regionName = regionName,
            city = city,
            zip = zip,
            lat = lat,
            lon = lon,
            timezone = timezone,
            isp = isp,
            org = org,
            _as = _as
        )
    }
}
