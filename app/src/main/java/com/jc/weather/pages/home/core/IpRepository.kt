package com.jc.weather.pages.home.core

import com.jc.weather.data.ip_api.IpApi
import com.jc.weather.domain.ip_api.mapper.IpDtoMapper
import com.jc.weather.domain.ip_api.model.IpDomainModel
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val api: IpApi,
    private val mapper: IpDtoMapper
) {

    suspend fun fetchLocation(): IpDomainModel {
        val ipDto = api.fetchLocation()
        return mapper.mapToDomain(ipDto)
    }
}
