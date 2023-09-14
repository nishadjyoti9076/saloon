package com.shashank.platform.saloon.model

data class SaveProductAllocationRequest(
    val allocatedQuantity: Int,
    val createOn: String,
    val createdBy: Int,
    val id: Int,
    val productId: Int,
    val totalAvailableQuantity: Int,
    val userId: Int
)