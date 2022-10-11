package com.jc.weather.ip_api.domain.repository

import com.jc.weather.api_client.di.NetworkResult
import com.jc.weather.api_client.di.handleApi
import com.jc.weather.ip_api.data.api.IpApi
import com.jc.weather.ip_api.data.dto.IpDto
import com.jc.weather.ip_api.domain.mapper.IpDtoMapper
import com.jc.weather.ip_api.domain.model.IpDomainResult
import javax.inject.Inject

class IpRepository @Inject constructor(
    private val api: IpApi,
    private val mapper: IpDtoMapper
) {

    suspend fun fetchLocation(): IpDomainResult =
        when (val result: NetworkResult<IpDto, Nothing> = handleApi { api.fetchLocation() }) {
            is NetworkResult.Success -> {
                val ipDto = result.data
                mapper.mapToDomain(ipDto)
            }
            is NetworkResult.Error -> {
                val errorCode = result.code
                val errorMessage = result.message
                IpDomainResult.Failure("Error code: $errorCode, message: $errorMessage")
            }
            is NetworkResult.Exception -> {
                val errorMessage = result.error
                IpDomainResult.Failure("Error message: $errorMessage")
            }
        }
}
