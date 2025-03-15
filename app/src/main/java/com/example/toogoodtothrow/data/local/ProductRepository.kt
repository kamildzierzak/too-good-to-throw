package com.example.toogoodtothrow.data.local

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductRepository(private val productDao: ProductDao) : IProductRepository {
    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
        .catch { e ->
            Log.e("ProductRepository", "Error fetching products", e)
            emit(emptyList())
        }

    override fun getProductById(productId: Int): Flow<Product?> = flow {
        try {
            emit(productDao.getProductById(productId))
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error fetching product with ID: $productId", e)
            emit(null)
        }
    }

    override suspend fun insertProduct(product: Product) {
        try {
            productDao.insertProduct(product)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error inserting product: $product", e)
        }
    }

    override suspend fun updateProduct(product: Product) {
        try {
            productDao.updateProduct(product)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error updating product: $product", e)
        }
    }

    override suspend fun deleteProduct(product: Product) {
        try {
            productDao.deleteProduct(product)
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error deleting product: $product", e)
        }
    }

    override suspend fun deleteDiscardedProducts() {
        try {
            productDao.deleteDiscardedProducts()
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error deleting discarded products", e)
        }
    }
}