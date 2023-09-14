package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.ServiceLeadRequest
import com.shashank.platform.saloon.model.UserLeadsResponseByUserIdItem
import com.shashank.platform.saloon.retrofit.ApiRepository

class UserLeadsViewModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<List<UserLeadsResponseByUserIdItem>>()
    var errorObserver = MutableLiveData<String>()
    val saveLeadData = MutableLiveData<ServiceLeadRequest>()
    val saveLeadDataResponse = MutableLiveData<List<ServiceLeadRequest>>()


    fun getLeadsDatabyUserId() {
        setIsLoading(true)
        ApiRepository.getLeadsDatabyUserId(allApplicationObserver, errorObserver)
    }

    fun saveLeadData(saveLeadData: ServiceLeadRequest) {
        setIsLoading(true)
        ApiRepository.saveLeads(saveLeadData, saveLeadDataResponse, errorObserver)
    }
}