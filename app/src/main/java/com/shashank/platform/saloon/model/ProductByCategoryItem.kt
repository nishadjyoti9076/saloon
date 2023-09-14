package com.shashank.platform.saloon.model

data class ProductByCategoryItem(
    val allocatProduct: Any,
    val availableQuantity: Any,
    val categoryId: Int,
    val companyName: String,
    val expirydate: String,
    val id: Int,
    val minmumSellingPrice: Double,
    val name: String,
    val productNameId: Any,
    val totalQuantity: Int,
    val totalAvailableQuantity: Int
)
