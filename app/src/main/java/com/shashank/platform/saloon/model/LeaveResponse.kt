package com.shashank.platform.saloon.model

data class LeaveResponse(
    val createOn: String,
    val createdBy: Int,
    val fromDate: String,
    val id: Int,
    val isActive: Boolean,
    val reason: String,
    val subject: Any,
    val toDate: String,
    val user: Any,
    val userId: Int
)