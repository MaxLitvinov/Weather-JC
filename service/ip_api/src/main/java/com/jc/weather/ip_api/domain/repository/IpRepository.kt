package com.jc.weather.ip_api.domain.repository

import com.jc.weather.ip_api.data.api.IpApi
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainModel
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
