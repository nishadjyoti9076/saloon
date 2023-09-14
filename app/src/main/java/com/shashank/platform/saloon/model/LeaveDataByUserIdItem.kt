package com.shashank.platform.saloon.model

data class LeaveDataByUserIdItem(
    val subject: String,
    val fromDate: String,
    val id: Int,
    val isActive: Boolean,
    val reason: String,
    val todate: String,
    val userId: Int,
    val status: Int
) : java.io.Serializable
