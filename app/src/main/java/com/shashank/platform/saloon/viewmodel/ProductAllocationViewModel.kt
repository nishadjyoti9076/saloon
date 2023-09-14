package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.*
import com.shashank.platform.saloon.retrofit.ApiRepository

class ProductAllocationViewModel(application: Application) :
    BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<List<GetProductAllocationDataItem>>()
    val productDeleteResponse = MutableLiveData<ProductDeleteResponse>()

    var errorObserver = MutableLiveData<String>()
    var deleteerrorObserver = MutableLiveData<String>()

    val updateproductResponse = MutableLiveData<UpdateProuctRequestResponse>()


    fun getProductAllocationData() {
        setIsLoading(true)
        ApiRepository.getProductAllocationData(allApplicationObserver, errorObserver)
    }

    fun deleteProductAllocationData(mDialog: SweetAlertDialog, productId: Int) {
        setIsLoading(true)
        ApiRepository.deleteProductAllocation(productDeleteResponse, errorObserver, mDialog,productId)
    }

    fun updateProductAllocationRequestData(updateProductAllocationRequest: UpdateProductAllocation2) {
        setIsLoading(true)
        ApiRepository.updateProductAllocationRequestData(
            updateProductAllocationRequest,
            updateproductResponse,
            errorObserver
        )
    }

}