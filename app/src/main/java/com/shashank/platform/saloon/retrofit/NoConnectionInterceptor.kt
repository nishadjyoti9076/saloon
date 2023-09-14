package com.shashank.platform.saloon.retrofit

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class NoConnectionInterceptor constructor(private val context: Context) : Interceptor {

    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (isConnectionOn()) {
            throw NoConnectivityException()

        } else if (isInternetAvailable()) {
            throw NoInternetConnection()
        } else {
            chain.proceed(chain.request())
        }
    }


    private fun isInternetAvailable(): Boolean {
        return try {
            // Check if there's an active internet connection by connecting to a reliable server
            val timeoutMs = 1500 // Adjust the timeout as needed
            val socket = Socket()
            val socketAddress = InetSocketAddress("www.google.com", 80)
            socket.connect(socketAddress, timeoutMs)
            socket.close()
            true
        } catch (e: Exception) {
            // If the connection fails, return false
            false
        }
    }

    class NoConnectivityException : IOException() {

        override val message: String?
            get() = "No internet connection"
    }

    class NoInternetConnection : IOException() {
        override val message: String?
            get() = "No internet connection"
    }


}
