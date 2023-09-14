package com.shashank.platform.saloon.retrofit

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.mContext
import com.shashank.platform.saloon.application.MySaloonApplication.Companion.sharedPreferenceClass
import com.shashank.platform.saloon.constant.ApiConst.ID
import com.shashank.platform.saloon.constant.GlobalAccess
import com.shashank.platform.saloon.model.*
import com.shashank.platform.saloon.ui.NewLoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object ApiRepository {

    private val TAG = ApiRepository::class.simpleName

    private val mApiService by lazy {
        ApiClient.create()
    }

    fun loginAPI(
        loginData: LoginData,
        allApplicationObserver: MutableLiveData<LoginDataResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sendLoginData = mApiService.login(loginData)
                Log.d("loginAPI", "loginAPI: $sendLoginData")
                if (sendLoginData.code() == 400 || sendLoginData.code() == 404 || sendLoginData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${sendLoginData.code()}")
                } else {
                    allApplicationObserver.postValue(sendLoginData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun logoutAPI(
        allApplicationObserver: MutableLiveData<String>,
        errorObserver: MutableLiveData<String>,
        mDialog: SweetAlertDialog
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sendLoginData = mApiService.logout()
                /*if (sendLoginData.isSuccessful) {
                    Log.d("logoutAPI", "logoutAPI: $sendLoginData")
                    mDialog.dismiss()
                    //allApplicationObserver.postValue("Logout$sendLoginData")
                }*/

                Toast.makeText(mContext, "Logout Sucessfully", Toast.LENGTH_LONG).show()
                val intent = Intent(mContext, NewLoginActivity::class.java)
                mContext?.startActivity(intent)
                // Assuming `sendLoginData` is the response you're interested in
            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                // errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getLeaveDatabyUserId(
        allApplicationObserver: MutableLiveData<List<LeaveDataByUserIdItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData = mApiService.getLeaveDatabyUserId(
                    sharedPreferenceClass.getString(ID)
                )
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getProductByUserId(
        allApplicationObserver: MutableLiveData<List<ProductDatabyUserIdItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData =
                    mApiService.getProductByUserId(sharedPreferenceClass.getString(ID))
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }

    fun getLeadsDatabyUserId(
        allApplicationObserver: MutableLiveData<List<UserLeadsResponseByUserIdItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData =
                    mApiService.getLeadsDatabyUserId(sharedPreferenceClass.getString(ID))
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun callForApplyLeave(
        leavedata: LeaveDataByUserIdItem,
        allLeaveObserver: MutableLiveData<LeaveResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sendLoginData = mApiService.applyLeave(leavedata)
                Log.d("loginAPI", "loginAPI: $sendLoginData")
                if (sendLoginData.code() == 400 || sendLoginData.code() == 404 || sendLoginData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${sendLoginData.code()}")
                } else {
                    allLeaveObserver.postValue(sendLoginData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }

    fun deleteLeave(
        errorObserver: MutableLiveData<String>,
        id: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                GlobalAccess.removeItemStatus=false
                val getLeaveData = mApiService.deleteLeave(id.toString()/*sharedPreferenceClass.getString(ID)*/)
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    //errorObserver.postValue("An error occurred: ${getLeaveData.code()}")

                } else {
                    // allApplicationObserver.postValue(getLeaveData.body())
//                    GlobalAccess.removeItemStatus=true
                    Toast.makeText(mContext, "Delete SuccessFully", Toast.LENGTH_LONG).show()

                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun updateLeave(
        leaveDataByUserIdItem: LeaveDataByUserIdItem,
        allApplicationObserver: MutableLiveData<LeaveResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData = mApiService.updateLeave(leaveDataByUserIdItem)
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    //errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                //errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun callForProductRequest(
        productData: SaveProductAllocationRequest,
        allLeaveObserver: MutableLiveData<ProductRequest>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productReesponse = mApiService.callProductRequest(productData)
                Log.d("loginAPI", "loginAPI: $productReesponse")
                if (productReesponse.code() == 400 || productReesponse.code() == 404 || productReesponse.code() == 500) {
                    errorObserver.postValue("An error occurred: ${productReesponse.code()}")
                } else {
                    allLeaveObserver.postValue(productReesponse.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getCategoryList(
        allApplicationObserver: MutableLiveData<List<CategoryListItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData = mApiService.getCategoryList()
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getProductByCategoryId(
        id: Int,
        allApplicationObserver: MutableLiveData<List<ProductByCategoryItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getLeaveData = mApiService.getProductByCategoryId(id)
                Log.d("getLeaveData", "getLeaveData: $getLeaveData")
                if (getLeaveData.code() == 400 || getLeaveData.code() == 404 || getLeaveData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getLeaveData.code()}")
                } else {
                    allApplicationObserver.postValue(getLeaveData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getUserProfile(
        allApplicationObserver: MutableLiveData<UserProfile>, errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getProfileData = mApiService.getUserProfile(sharedPreferenceClass.getString(ID))
                Log.d("getProfileData", "getProfileData: $getProfileData")
                if (getProfileData.code() == 400 || getProfileData.code() == 404 || getProfileData.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getProfileData.code()}")
                } else {
                    allApplicationObserver.postValue(getProfileData.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun updateProfile(
        userProfileRequest: UpdateProfileRequest,
        updateProfileResponseobj: MutableLiveData<UpdateProfileResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val updateProfileResponse1 = mApiService.updateProfile(userProfileRequest)
                Log.d("loginAPI", "loginAPI: $updateProfileResponse1")
                if (updateProfileResponse1.code() == 400 || updateProfileResponse1.code() == 404 || updateProfileResponse1.code() == 500) {
                    errorObserver.postValue("An error occurred: ${updateProfileResponse1.code()}")
                } else {
                    updateProfileResponseobj.postValue(updateProfileResponse1.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun saveLeads(
        userProfileRequest: ServiceLeadRequest,
        saveLeadResponse: MutableLiveData<List<ServiceLeadRequest>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val leadResponse = mApiService.calServiceLeads(
                    userProfileRequest
                )
                Log.d("loginAPI", "leadResponse: $leadResponse")
                if (leadResponse.code() == 400 || leadResponse.code() == 404 || leadResponse.code() == 500) {
                    errorObserver.postValue("An error occurred: ${leadResponse.code()}")
                } else {
                    saveLeadResponse.postValue(leadResponse.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun getProductAllocationData(
        allApplicationObserver: MutableLiveData<List<GetProductAllocationDataItem>>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val getProfileAllocationDataItem =
                    mApiService.getProductAllocation(sharedPreferenceClass.getString(ID))
                Log.d("getProfileData", "getProfileData: $getProfileAllocationDataItem")
                if (getProfileAllocationDataItem.code() == 400 || getProfileAllocationDataItem.code() == 404 || getProfileAllocationDataItem.code() == 500) {
                    errorObserver.postValue("An error occurred: ${getProfileAllocationDataItem.code()}")
                } else {
                    allApplicationObserver.postValue(getProfileAllocationDataItem.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun updateProductAllocationData(
        updateProductAllocationRequest: UpdateProductAllocationRequest,
        updateproductResponse: MutableLiveData<UpdateProuctRequestResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val updateProfileAllocationDataItem =
                    mApiService.updateProductRequest(updateProductAllocationRequest)
                Log.d("getProfileData", "getProfileData: $updateProfileAllocationDataItem")
                if (updateProfileAllocationDataItem.code() == 400 || updateProfileAllocationDataItem.code() == 404 || updateProfileAllocationDataItem.code() == 500) {
                    errorObserver.postValue("An error occurred: ${updateProfileAllocationDataItem.code()}")
                } else {
                    updateproductResponse.postValue(updateProfileAllocationDataItem.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }
    fun deleteProductAllocation(
        productDeleteResponse: MutableLiveData<ProductDeleteResponse>,
        errorObserver: MutableLiveData<String>, mDialog: SweetAlertDialog, productId: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val deleteProduct =
                    mApiService.deleteProductRequestAllocation(productId.toString())
                Log.d("getLeaveData", "getLeaveData: $deleteProduct")
                if (deleteProduct.code() == 400 || deleteProduct.code() == 404 || deleteProduct.code() == 500) {
                    errorObserver.postValue("An error occurred: ${deleteProduct.code()}")
                } else {
                    productDeleteResponse.postValue(deleteProduct.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }


    fun updateProductAllocationRequestData(
        updateProductAllocationRequest: UpdateProductAllocation2,
        updateproductResponse: MutableLiveData<UpdateProuctRequestResponse>,
        errorObserver: MutableLiveData<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val updateProfileAllocationDataItem =
                    mApiService.updateProductAllocationRequestData(updateProductAllocationRequest)
                Log.d("Prod", "getProfileData: $updateProfileAllocationDataItem")
                if (updateProfileAllocationDataItem.code() == 400 || updateProfileAllocationDataItem.code() == 404 || updateProfileAllocationDataItem.code() == 500) {
                    errorObserver.postValue("An error occurred: ${updateProfileAllocationDataItem.code()}")
                } else {
                    updateproductResponse.postValue(updateProfileAllocationDataItem.body())
                }
                // Assuming `sendLoginData` is the response you're interested in

            } catch (exception: Exception) {
                Log.e(TAG, "API call failed: ${exception.message}", exception)
                errorObserver.postValue("An error occurred: ${exception.message}")
            }
        }
    }

}