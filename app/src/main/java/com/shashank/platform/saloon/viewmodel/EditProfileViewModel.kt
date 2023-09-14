package com.shashank.platform.saloon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shashank.platform.saloon.base.BaseViewModel
import com.shashank.platform.saloon.model.UpdateProfileRequest
import com.shashank.platform.saloon.model.UpdateProfileResponse
import com.shashank.platform.saloon.model.UserProfile
import com.shashank.platform.saloon.retrofit.ApiRepository

class EditProfileViewModel(application: Application) : BaseViewModel<Application>(application) {


    val userProfileData = MutableLiveData<UserProfile>()
    val updateProfileResponse = MutableLiveData<UpdateProfileResponse>()
    var errorObserver = MutableLiveData<String>()


    fun getProfileData() {
        setIsLoading(true)
        ApiRepository.getUserProfile(
            userProfileData,
            errorObserver
        )
    }

    fun updateProfile(updateProfile: UpdateProfileRequest) {
        setIsLoading(true)
        ApiRepository.updateProfile(
            updateProfile,
            updateProfileResponse,
            errorObserver
        )
    }

}