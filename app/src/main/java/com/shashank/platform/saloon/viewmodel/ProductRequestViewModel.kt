package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.*
import com.shashank.platform.saloon.retrofit.ApiRepository

class ProductRequestViewModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<ProductRequest>()
    val getCategoryObserver = MutableLiveData<List<CategoryListItem>>()
    val getProductByCategoryItem = MutableLiveData<List<ProductByCategoryItem>>()

    var errorObserver = MutableLiveData<String>()
    var mCategory = MutableLiveData<String>()
    var mProductName = MutableLiveData<String>()
    var mQuality = MutableLiveData<String>()
    var mPrice = MutableLiveData<String>()
    var mAmount = MutableLiveData<String>()

    fun callForProductRequest(productDatarequest: SaveProductAllocationRequest) {
        setIsLoading(true)
        ApiRepository.callForProductRequest(
            productDatarequest,
            allApplicationObserver,
            errorObserver
        )
    }

    fun getCategoryList() {

        setIsLoading(true)
        ApiRepository.getCategoryList(
            getCategoryObserver,
            errorObserver
        )
    }


    fun getProductByCategoryId(id: Int?) {
        setIsLoading(true)
        if (id != null) {
            ApiRepository.getProductByCategoryId(
                id,
                getProductByCategoryItem,
                errorObserver
            )
        }
    }


    fun onMyButtonClicked() {
        /*    setIsLoading(true)
            ApiRepository.callForApplyLeave(leaveDatabyUSerIdItem, allApplicationObserver, errorObserver)*/
    }
}
