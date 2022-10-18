package com.jc.weather.api_client.di.factory.network_response

import retrofit2.Response
import java.io.IOException

sealed class NetworkResponse<S : Any, F : Any> {

    data class Success<S : Any, F : Any>(
        val body: S,
        val response: Response<*>
    ) : NetworkResponse<S, F>() {

        val code: Int get() = response.code()
    }

    data class ServerError<S : Any, F : Any>(
        override val body: F?,
        val response: Response<*>?
    ) : Error<S, F>() {

        val code: Int? get() = response?.code()

        override val error: Throwable? get() = null
    }

    data class NetworkError<S : Any, F : Any>(
        override val error: IOException
    ) : Error<S, F>() {
        override val body: F? get() = null
    }

    data class UnknownError<S : Any, F : Any>(
        override val error: Throwable,
        val response: Response<*>?
    ) : Error<S, F>() {

        override val body: F? get() = null

        val code: Int? = response?.code()
    }

    sealed class Error<S : Any, F : Any> : NetworkResponse<S, F>() {

        abstract val body: F?
        abstract val error: Throwable?
    }
}

suspend fun <S : Any, F : Any> NetworkResponse<S, F>.onSuccess(
    executable: suspend(S) -> Unit
): NetworkResponse<S, F> = apply {
    if (this is NetworkResponse.Success) {
        executable(body)
    }
}

suspend fun <S : Any, F : Any> NetworkResponse<S, F>.onServerError(
    executable: suspend(code: Int?, message: String?) -> Unit
): NetworkResponse<S, F> = apply {
    if (this is NetworkResponse.ServerError) {
        executable(code, error?.message)
    }
}

suspend fun <S : Any, F : Any> NetworkResponse<S, F>.onNetworkError(
    executable: suspend(message: String?) -> Unit
): NetworkResponse<S, F> = apply {
    if (this is NetworkResponse.NetworkError) {
        executable(error.message)
    }
}

suspend fun <S : Any, F : Any> NetworkResponse<S, F>.onUnknownError(
    executable: suspend(message: String?) -> Unit
): NetworkResponse<S, F> = apply {
    if (this is NetworkResponse.UnknownError) {
        executable(error.message)
    }
}
