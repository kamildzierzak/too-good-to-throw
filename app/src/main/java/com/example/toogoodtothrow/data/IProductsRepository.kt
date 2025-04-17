package com.example.toogoodtothrow.data

import kotlinx.coroutines.flow.Flow

interface IProductsRepository {

    fun getAllProducts(): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product>

    fun getProductsByCategory(category: ProductCategory): Flow<List<Product>>

    fun getProductsByExpiredStatus(isExpired: Boolean): Flow<List<Product>>

    suspend fun insertProduct(product: Product)

    suspend fun insertAllProducts(products: List<Product>)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)
}