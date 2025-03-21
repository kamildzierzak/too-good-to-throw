package com.example.toogoodtothrow.data

import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    val allProducts: Flow<List<Product>>
    val validProducts: Flow<List<Product>>
    val expiredProducts: Flow<List<Product>>

    fun getProductsByCategory(category: ProductCategory): Flow<List<Product>>
    fun getProductById(productId: Int): Flow<Product?>

    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun discardProduct(productId: Int)

//    suspend fun deleteDiscardedProducts()
}