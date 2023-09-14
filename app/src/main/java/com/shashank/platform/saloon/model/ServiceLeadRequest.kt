package com.shashank.platform.saloon.model

data class ServiceLeadRequest(
    val address: String,
    val destinationLocation: String,
    val isActive: Boolean,
    val latitude: Double,
    val leadsId: Int,
    val longitude: Double,
    val sourceLocation: String,
    val status: Int,
    val userId: Int
)