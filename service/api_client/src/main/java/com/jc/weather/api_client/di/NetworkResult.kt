package com.jc.weather.api_client.di

import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<out T : Any, out E : Any> {

    class Success<T : Any>(val data: T) : NetworkResult<T, Nothing>()
    class Error<E : Any>(val code: Int, val message: String?) : NetworkResult<Nothing, E>()
    class Exception<E : Any>(val error: Throwable) : NetworkResult<Nothing, E>()
}

suspend fun <T : Any, E : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T, E> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), message = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}
