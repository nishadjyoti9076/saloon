package com.shashank.platform.saloon.retrofit

import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.constant.ApiConst.ACCESS_TOKEN
import okhttp3.Interceptor

object RetrofitInterceptor {


    fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer ${sharedPreferenceClass.getString(ACCESS_TOKEN)}"
                )
                .build()

        )
    }


}