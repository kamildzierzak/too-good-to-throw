package com.example.toogoodtothrow.domain.repository

import com.example.toogoodtothrow.data.IProductRepository
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productDao: ProductDao) :
    IProductRepository {
    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override fun getProductById(productId: Int): Flow<Product?> =
        productDao.getProductById(productId)

    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)

    override suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)

    override suspend fun deleteDiscardedProducts() = productDao.deleteDiscardedProducts()
}