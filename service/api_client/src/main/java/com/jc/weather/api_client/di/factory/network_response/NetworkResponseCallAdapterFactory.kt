package com.jc.weather.api_client.di.factory.network_response

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {

        private const val SUCCESS_TYPE = 0
        private const val ERROR_TYPE = 1

        fun create(): NetworkResponseCallAdapterFactory = NetworkResponseCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (returnType !is ParameterizedType) {
            return null
        }

        val containerType = getParameterUpperBound(SUCCESS_TYPE, returnType)
        if (getRawType(containerType) != NetworkResponse::class.java) {
            return null
        }

        if (containerType !is ParameterizedType) {
            return null
        }

        val (successBodyType, errorBodyType) = containerType.getBodyTypes()
        val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(null, errorBodyType, annotations)

        return when (getRawType(returnType)) {
            Call::class.java -> NetworkResponseAdapter<Any, Any>(successBodyType, errorBodyConverter)
            else -> null
        }
    }

    private fun ParameterizedType.getBodyTypes(): Pair<Type, Type> {
        val successType = getParameterUpperBound(SUCCESS_TYPE, this)
        val errorType = getParameterUpperBound(ERROR_TYPE, this)
        return successType to errorType
    }
}
