package com.shashank.platform.saloon.constant

object ApiConst {

    const val LOGIN = "User/Login/Login"
    const val LOGOUT = "User/Logout/Logout"
    const val GET_LEAVE_DATA_BY_USER_ID = "UserLeaves/GetLeaveByUserId"
    const val GET_PRODUCT_DATA_BY_USER_ID = "ProductAllocation/GetPartnerStockByPartnerId/{id}"
    const val GET_USER_LEADS_DATA_BY_USER_ID = "ServiceLeads/GetLeadsByPartnerId/{id}"
    const val APPLY_LEAVE = "UserLeaves/AddLeave"
    const val ONE_TIME_LOGIN = "oneTimeLogin"
    const val ACCESS_TOKEN = "AccessToken"
    const val ID = "Id"
    const val USER_NAME = "username"
    const val UPDATE_LEAVE = "UserLeaves/UpdateLeave"
    const val DELETE_LEAVE = "UserLeaves/Delete"
    const val PRODUCT_REQUEST = "ProductAllocationRequest/SaveProductAllocationRequest"
    const val CATAGORY_LIST = "ProductAllocation/GetAllCategory"
    const val PRODUCT_BY_CATAGORY = "ProductAllocation/GetProductByCalegoryId/{id}"
    const val USER_PROFILE = "User/GetUserProfile/{id}"
    const val UPDATE_PROFILE = "User/UpdateUserProfile"
    const val SAVE_LEADS = "ServiceLeads/UpdateServiceLeads"
    const val PRODUCT_ALLOCATION = "ProductAllocationRequest/GetProductByUserID/{id}"
    const val UPDATE_PRODUCT = "ProductAllocation/UpdatePartnerProductStock"
    const val UPDATE_PRODUCT_ALLOCATION = "ProductAllocationRequest/UpdatePartnerProductRequest"
    const val DELECT_PRODUCT_ALLOCATION = "ProductAllocationRequest/Delete/{id}"
}