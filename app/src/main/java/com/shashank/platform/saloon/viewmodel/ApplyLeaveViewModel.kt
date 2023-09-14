package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.LeaveDataByUserIdItem
import com.shashank.platform.saloon.model.LeaveResponse
import com.shashank.platform.saloon.retrofit.ApiRepository


class ApplyLeaveViewModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<LeaveResponse>()
    val deleteObserver = MutableLiveData<String>()

    var errorObserver = MutableLiveData<String>()
    val selectedToDateText = ObservableField("")
    val selectedFromDateText = ObservableField("")
    val selectedSubject = ObservableField("")
    val selectReason = ObservableField("")


    fun callApplyLeave(leaveDataByUserIdItem: LeaveDataByUserIdItem) {
        setIsLoading(true)

        ApiRepository.callForApplyLeave(
            leaveDataByUserIdItem,
            allApplicationObserver,
            errorObserver
        )
    }


    fun updateLeave(leaveDataByUserIdItem: LeaveDataByUserIdItem) {
        setIsLoading(true)
        ApiRepository.updateLeave(
            leaveDataByUserIdItem,
            allApplicationObserver,
            errorObserver
        )
    }



}