package com.shashank.platform.saloon.model

data class ProductDatabyUserIdItem(
    val allocatedQuantity: Int,
    val categoryId: Any,
    val categoryName: String,
    val companyName: String,
    val expirydate: String,
    val hdnData: Any,
    val id: Int,
    val lstProductDto: Any,
    val maxmumSellingPrice: Any,
    val menufechardate: String,
    val minmumSellingPrice: Double,
    val productId: Int,
    val productName: String,
    val totalAvailableQuantity: Int,
    val totalPrice: String,
    val totalSellingQuantity: Int,
    val userId: Int
)