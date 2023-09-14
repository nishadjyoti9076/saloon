package com.shashank.platform.saloon.model

data class UserProfile(
    val dob: String,
    val firstName: String,
    val lastName: String,
    val mobileNo: String,
    val photo: Any,
    val profileImage: String
)