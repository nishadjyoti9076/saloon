package com.shashank.platform.saloon.retrofit

import com.shashank.platform.saloon.constant.ApiConst.APPLY_LEAVE
import com.shashank.platform.saloon.constant.ApiConst.CATAGORY_LIST
import com.shashank.platform.saloon.constant.ApiConst.DELECT_PRODUCT_ALLOCATION
import com.shashank.platform.saloon.constant.ApiConst.DELETE_LEAVE
import com.shashank.platform.saloon.constant.ApiConst.GET_LEAVE_DATA_BY_USER_ID
import com.shashank.platform.saloon.constant.ApiConst.GET_PRODUCT_DATA_BY_USER_ID
import com.shashank.platform.saloon.constant.ApiConst.GET_USER_LEADS_DATA_BY_USER_ID
import com.shashank.platform.saloon.constant.ApiConst.LOGIN
import com.shashank.platform.saloon.constant.ApiConst.LOGOUT
import com.shashank.platform.saloon.constant.ApiConst.PRODUCT_ALLOCATION
import com.shashank.platform.saloon.constant.ApiConst.PRODUCT_BY_CATAGORY
import com.shashank.platform.saloon.constant.ApiConst.PRODUCT_REQUEST
import com.shashank.platform.saloon.constant.ApiConst.SAVE_LEADS
import com.shashank.platform.saloon.constant.ApiConst.UPDATE_LEAVE
import com.shashank.platform.saloon.constant.ApiConst.UPDATE_PRODUCT
import com.shashank.platform.saloon.constant.ApiConst.UPDATE_PRODUCT_ALLOCATION
import com.shashank.platform.saloon.constant.ApiConst.UPDATE_PROFILE
import com.shashank.platform.saloon.constant.ApiConst.USER_PROFILE
import com.shashank.platform.saloon.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST(LOGIN)
    suspend fun login(@Body loginData: LoginData): Response<LoginDataResponse>

    @POST(LOGOUT)
    suspend fun logout(): Response<String>

    @POST(APPLY_LEAVE)
    suspend fun applyLeave(@Body loginData: LeaveDataByUserIdItem): Response<LeaveResponse>


    @GET(GET_LEAVE_DATA_BY_USER_ID)
    suspend fun getLeaveDatabyUserId(@Query("id") id: String): Response<List<LeaveDataByUserIdItem>>


    @GET(GET_PRODUCT_DATA_BY_USER_ID)
    suspend fun getProductByUserId(@Path("id") id: String): Response<List<ProductDatabyUserIdItem>>

    @GET(GET_USER_LEADS_DATA_BY_USER_ID)
    suspend fun getLeadsDatabyUserId(@Path("id") id: String): Response<List<UserLeadsResponseByUserIdItem>>

    @POST(UPDATE_LEAVE)
    suspend fun updateLeave(@Body loginData: LeaveDataByUserIdItem): Response<LeaveResponse>

    @POST(DELETE_LEAVE)
    suspend fun deleteLeave(@Query("id") id: String): Response<String>


    /*  @POST(PRODUCT_REQUEST)
      suspend fun callProductRequest(@Body loginData: ProductRequest): Response<ProductRequest>*/


    @POST(PRODUCT_REQUEST)
    suspend fun callProductRequest(@Body loginData: SaveProductAllocationRequest): Response<ProductRequest>


    @GET(CATAGORY_LIST)
    suspend fun getCategoryList(): Response<List<CategoryListItem>>

    @GET(PRODUCT_BY_CATAGORY)
    suspend fun getProductByCategoryId(@Path("id") id: Int): Response<List<ProductByCategoryItem>>

    @GET(USER_PROFILE)
    suspend fun getUserProfile(@Path("id") id: String): Response<UserProfile>


    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @Body loginData: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @POST(SAVE_LEADS)
    suspend fun calServiceLeads(@Body loginData: ServiceLeadRequest): Response<List<ServiceLeadRequest>>


    @GET(PRODUCT_ALLOCATION)
    suspend fun getProductAllocation(@Path("id") id: String): Response<List<GetProductAllocationDataItem>>


    @POST(UPDATE_PRODUCT)
    suspend fun updateProductRequest(@Body updateProductAllocationRequest: UpdateProductAllocationRequest): Response<UpdateProuctRequestResponse>


    @POST(UPDATE_PRODUCT_ALLOCATION)
    suspend fun updateProductAllocationRequestData(@Body updateProductAllocation2: UpdateProductAllocation2): Response<UpdateProuctRequestResponse>


    @POST(DELECT_PRODUCT_ALLOCATION)
    suspend fun deleteProductRequestAllocation(@Path("id") id: String):Response<ProductDeleteResponse>
}