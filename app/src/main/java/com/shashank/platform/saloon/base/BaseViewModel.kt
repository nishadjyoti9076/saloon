package com.shashank.platform.saloon.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {
    var isLoading = ObservableField(false)

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }
}