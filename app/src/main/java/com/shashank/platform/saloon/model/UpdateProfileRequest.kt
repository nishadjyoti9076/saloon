package com.shashank.platform.saloon.model

data class UpdateProfileRequest(
    val firstName: String,
    val id: Int,
    val lastName: String,
    val mobileNo: String,
    val dob: String
)