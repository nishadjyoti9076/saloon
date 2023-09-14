package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.LoginData
import com.shashank.platform.saloon.model.LoginDataResponse
import com.shashank.platform.saloon.retrofit.ApiRepository

class LoginViewModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<LoginDataResponse>()
    var errorObserver = MutableLiveData<String>()


    fun getLogin(loginData: LoginData) {
        setIsLoading(true)
        ApiRepository.loginAPI(loginData, allApplicationObserver, errorObserver)
    }

}