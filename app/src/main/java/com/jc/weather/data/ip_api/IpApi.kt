package com.jc.weather.data.ip_api

import retrofit2.http.GET

interface IpApi {

    @GET("json/")
    suspend fun fetchLocation(): IpDto
}
