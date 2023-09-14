package com.shashank.platform.saloon.model

data class LoginDataResponse(
    val email: String,
    val id: Int,
    val name: String,
    val photo: Any,
    val token: String,
    val deviceId:String,
    val walletAmount: Int
)