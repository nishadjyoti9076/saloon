package com.shashank.platform.saloon.model

data class UpdateProuctRequestResponse(
    val allocatedQuantity: Int,
    val createOn: String,
    val createdBy: Int,
    val id: Int,
    val isActive: Boolean,
    val product: Any,
    val productId: Int,
    val totalAvailableQuantity: Int,
    val totalSellingQuantity: Int,
    val updatedBy: Any,
    val updatedOn: Any,
    val user: Any,
    val userId: Int
)