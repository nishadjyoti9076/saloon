package com.shashank.platform.saloon.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

class ApiClient {
    companion object {
        fun create(
            connectTimeOutInsec: Long = NetworkConstants.DEFAULT_CONNECTION_TIMEOUT_SEC,
            readTimeoutSec: Long = NetworkConstants.DEFAULT_READ_TIME_OUT,
            writeTimeoutSec: Long = NetworkConstants.DEFAULT_WRITE_TIME_OUT
        ): ApiInterface {
            val client = OkHttpClient.Builder()
            client.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(loggingInterceptor)
            client.addInterceptor(RetrofitInterceptor.headersInterceptor())
            // client.authenticator()
            //  client.addNetworkInterceptor(NoConnectionInterceptor(mContext!!))
            client.connectTimeout(connectTimeOutInsec, TimeUnit.SECONDS)
            client.readTimeout(readTimeoutSec, TimeUnit.SECONDS)
            client.writeTimeout(writeTimeoutSec, TimeUnit.SECONDS)
            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                // .baseUrl("https://mysalon.dreamitsolution.org/api/")
                .baseUrl("https://mysalonss.dreamitsolution.org/api/")
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}