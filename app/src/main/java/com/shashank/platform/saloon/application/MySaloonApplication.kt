package com.shashank.platform.saloon.application

import android.app.Application
import android.content.Context
import com.shashank.platform.saloon.constant.SharedPreferenceClass

class MySaloonApplication : Application() {

    companion object {
        lateinit var sharedPreferenceClass: SharedPreferenceClass
        var mContext: MySaloonApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        sharedPreferenceClass = SharedPreferenceClass(applicationContext)
    }

}