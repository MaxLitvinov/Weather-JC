package com.jc.weather.ip_api.domain.model

sealed class IpDomainResult {

    data class Success(
        val query: String,
        val status: String,
        val country: String,
        val countryCode: String,
        val region: String,
        val regionName: String,
        val city: String,
        val zip: String,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        val isp: String,
        val org: String,
        val _as: String
    ) : IpDomainResult()

    data class Failure(val error: String?) : IpDomainResult()
}
