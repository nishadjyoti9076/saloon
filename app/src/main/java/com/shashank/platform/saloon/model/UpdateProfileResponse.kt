package com.shashank.platform.saloon.model

data class UpdateProfileResponse(
    val aadharNumber: String,
    val address: String,
    val createOn: String,
    val createdBy: Int,
    val dob: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val isActive: Boolean,
    val isPortal: Boolean,
    val lastName: String,
    val mobileNo: String,
    val panNumber: String,
    val pincode: Any,
    val profile: String,
    val updatedBy: Any,
    val updatedOn: String,
    val user: Any,
    val userId: Int
)