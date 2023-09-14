package com.shashank.platform.saloon.model

data class UpdateProductAllocationRequest(
    val totalSellingQuantity: Int,
    val id: Int,
    val productId: Int,
    val userId: Int
)
