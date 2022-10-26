package com.jc.weather.ip_api.domain.repository

import com.jc.weather.api_client.di.factory.network_response.NetworkResponse
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
        when (val response: NetworkResponse<IpDto, Any> = api.fetchLocation()) {
            is NetworkResponse.Success ->
                mapper.mapToDomain(response.body)
            is NetworkResponse.ServerError ->
                IpDomainResult.Failure("Server error code - ${response.code}, message - ${response.error?.message}")
            is NetworkResponse.NetworkError ->
                IpDomainResult.Failure("Network error body - ${response.body}, message - ${response.error.message}")
            is NetworkResponse.UnknownError ->
                IpDomainResult.Failure("Unknown error code - ${response.code}")
        }
}
