package com.shashank.platform.saloon.model

data class UserLeadsResponseByUserIdItem(
    val address: String,
    val alloctedUserId: Int,
    val customerMobile: String,
    val customerName: String,
    val duration: String,
    val id: Int,
    val isActive: Boolean,
    val processEndTime: String,
    val processStartTime: Any,
    val serviceEndTime: Any,
    val serviceName: String,
    val serviceStartTime: String,
    val serviceType: String,
    val startLocation: Any,
    val stopLocation: Any,
    val userName: String
)