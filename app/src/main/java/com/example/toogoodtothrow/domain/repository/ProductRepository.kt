package com.example.toogoodtothrow.domain.repository

import com.example.toogoodtothrow.data.IProductRepository
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductCategory
import com.example.toogoodtothrow.data.ProductDao
import kotlinx.coroutines.flow.Flow


class ProductRepository(private val productDao: ProductDao) : IProductRepository {


    override val allProducts: Flow<List<Product>>
        get() = productDao.getAllProducts()

    override val validProducts: Flow<List<Product>>
        get() = productDao.getValidProducts()

    override val expiredProducts: Flow<List<Product>>
        get() = productDao.getExpiredProducts()

    override fun getProductsByCategory(category: ProductCategory): Flow<List<Product>> {
        return productDao.getProductsByCategory(category)
    }

    override fun getProductById(productId: Int): Flow<Product?> {
        return productDao.getProductById(productId)
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    override suspend fun discardProduct(productId: Int) {
        productDao.discardProduct(productId)
    }
}