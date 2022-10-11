package com.jc.weather.ip_api.data.api

import com.jc.weather.ip_api.data.dto.IpDto
import retrofit2.Response
import retrofit2.http.GET

interface IpApi {

    @GET("json/")
    suspend fun fetchLocation(): Response<IpDto>
}