package com.jc.weather.api_client.di.factory.network_response

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class NetworkResponseCall<S : Any, F : Any>(
    private val backingCall: Call<S>,
    private val errorConverter: Converter<ResponseBody, F>,
    private val successBodyType: Type
) : Call<NetworkResponse<S, F>> {

    override fun enqueue(callback: Callback<NetworkResponse<S, F>>) = synchronized(this) {
        backingCall.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val networkResponse = response.asNetworkResponse(successBodyType, errorConverter)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = throwable.asNetworkResponse<S, F>(successBodyType, errorConverter)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = synchronized(this) {
        backingCall.isExecuted
    }

    override fun clone(): Call<NetworkResponse<S, F>> = NetworkResponseCall(
        backingCall.clone(),
        errorConverter,
        successBodyType
    )

    override fun isCanceled(): Boolean = synchronized(this) {
        backingCall.isCanceled
    }

    override fun cancel() = synchronized(this) {
        backingCall.cancel()
    }

    override fun execute(): Response<NetworkResponse<S, F>> {
        val retrofitResponse = backingCall.execute()
        val networkResponse = retrofitResponse.asNetworkResponse(successBodyType, errorConverter)
        return Response.success(networkResponse)
    }

    override fun request(): Request = backingCall.request()

    override fun timeout(): Timeout = backingCall.timeout()
}

internal fun <S : Any, F : Any> Response<S>.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, F>
): NetworkResponse<S, F> {
    return if (!isSuccessful) {
        parseUnsuccessfulResponse(this, errorConverter)
    } else {
        parseSuccessfulResponse(this, successType)
    }
}

private fun <S : Any, F : Any> parseUnsuccessfulResponse(
    response: Response<S>,
    errorConverter: Converter<ResponseBody, F>
): NetworkResponse.Error<S, F> {
    val errorBody: ResponseBody = response.errorBody() ?: return NetworkResponse.ServerError(null, response)

    return try {
        val convertedBody = errorConverter.convert(errorBody)
        NetworkResponse.ServerError(convertedBody, response)
    } catch (error: Throwable) {
        NetworkResponse.UnknownError(error, response)
    }
}

private fun <S : Any, F : Any> parseSuccessfulResponse(
    response: Response<S>,
    successType: Type
): NetworkResponse<S, F> {
    val responseBody: S? = response.body()
    if (responseBody == null) {
        if (successType === Unit::class.java) {
            @Suppress("UNCHECKED_CAST")
            return NetworkResponse.Success<Unit, F>(Unit, response) as NetworkResponse<S, F>
        }
        return NetworkResponse.ServerError(null, response)
    }
    return NetworkResponse.Success(responseBody, response)
}

internal fun <S : Any, F : Any> Throwable.asNetworkResponse(
    successType: Type,
    errorConverter: Converter<ResponseBody, F>
): NetworkResponse<S, F> {
    return when (this) {
        is IOException -> NetworkResponse.NetworkError(this)
        is HttpException -> {
            val response = response()
            if (response == null) {
                NetworkResponse.ServerError(null, null)
            } else {
                @Suppress("UNCHECKED_CAST")
                response.asNetworkResponse(successType, errorConverter) as NetworkResponse<S, F>
            }
        }
        else -> NetworkResponse.UnknownError(this, null)
    }
}
