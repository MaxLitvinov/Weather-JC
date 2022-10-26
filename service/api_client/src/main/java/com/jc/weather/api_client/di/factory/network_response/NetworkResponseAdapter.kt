package com.jc.weather.api_client.di.factory.network_response

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseAdapter<S : Any, F : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, F>
) : CallAdapter<S, Call<NetworkResponse<S, F>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S, F>> {
        return NetworkResponseCall(call, errorBodyConverter, successType)
    }
}
