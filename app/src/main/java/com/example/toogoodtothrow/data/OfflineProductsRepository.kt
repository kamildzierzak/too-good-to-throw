package com.example.toogoodtothrow.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineProductsRepository @Inject constructor(private val productDao: ProductDao) :
    IProductsRepository {

    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override fun getProductById(id: Int): Flow<Product> = productDao.getProductById(id)

    override fun getProductsByCategory(category: ProductCategory): Flow<List<Product>> =
        productDao.getProductsByCategory(category)

    override fun getProductsByExpiredStatus(isExpired: Boolean): Flow<List<Product>> =
        productDao.getProductsByExpiredStatus(isExpired)

    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    override suspend fun insertAllProducts(products: List<Product>) {
        productDao.insertAll(products)
    }

    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)

    override suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}