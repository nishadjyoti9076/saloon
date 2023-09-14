package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.ProductDatabyUserIdItem
import com.shashank.platform.saloon.model.UpdateProductAllocationRequest
import com.shashank.platform.saloon.model.UpdateProuctRequestResponse
import com.shashank.platform.saloon.retrofit.ApiRepository

class ProductViewModel(application: Application) : BaseViewModel<Application>(application) {

    val allApplicationObserver = MutableLiveData<List<ProductDatabyUserIdItem>>()
    val updateproductResponse = MutableLiveData<UpdateProuctRequestResponse>()
    var errorObserver = MutableLiveData<String>()


    fun getProductByUserId() {
        setIsLoading(true)
        ApiRepository.getProductByUserId(allApplicationObserver, errorObserver)
    }


    fun updateProductAllocationData(updateProductAllocationRequest: UpdateProductAllocationRequest) {
        setIsLoading(true)
        ApiRepository.updateProductAllocationData(
            updateProductAllocationRequest,
            updateproductResponse,
            errorObserver
        )
    }
}