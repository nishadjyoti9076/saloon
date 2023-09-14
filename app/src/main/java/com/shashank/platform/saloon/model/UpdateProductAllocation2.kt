package com.shashank.platform.saloon.model

data class UpdateProductAllocation2(
    val allocatedQuantity: Int,
    val id: Int,
    val productId: Int,
    val userId: Int
)