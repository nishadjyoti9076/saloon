package com.shashank.platform.saloon.model

data class ProductRequest(
    val actionmassege: String,
    val allocatedQuantity: Int,
    val categoryName: String,
    val createOn: String,
    val createdBy: Int,
    val id: Int,
    val isActive: Boolean,
    val productId: Int,
    val productName: String,
    val status: Int,
    val totalAvailableQuantity: Int,
    val updatedBy: Int,
    val updatedOn: String,
    val userId: Int,
    val userName: String
)