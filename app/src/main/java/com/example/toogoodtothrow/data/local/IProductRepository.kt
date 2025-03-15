package com.example.toogoodtothrow.data.local

import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProductById(productId: Int): Flow<Product?>
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteDiscardedProducts()
}