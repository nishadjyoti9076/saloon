package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.LeaveDataByUserIdItem
import com.shashank.platform.saloon.model.ProductDeleteResponse
import com.shashank.platform.saloon.retrofit.ApiRepository

class LeaveDataByUserModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<List<LeaveDataByUserIdItem>>()
    var errorObserver = MutableLiveData<String>()


    fun getLeaveDataByUser() {
        setIsLoading(true)
        ApiRepository.getLeaveDatabyUserId(allApplicationObserver, errorObserver)
    }

    fun deleteLeave(id: Int) {
        setIsLoading(true)
        ApiRepository.deleteLeave(errorObserver,id)

    }
}