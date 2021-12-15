package com.mirkojovanovic.thelordoftheringsjourney.data.remote

import com.mirkojovanovic.thelordoftheringsjourney.common.Constants
import com.mirkojovanovic.thelordoftheringsjourney.common.signWithToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val signedRequest = chain.request().signWithToken(Constants.API_KEY)
        return chain.proceed(signedRequest)
    }

}