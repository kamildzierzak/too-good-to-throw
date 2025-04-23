package com.example.toogoodtothrow.data.repository

import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.ProductDao
import kotlinx.coroutines.flow.Flow

class ProductsRepository(private val productDao: ProductDao) :
    IProductsRepository {

    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()

    override fun getProductById(id: Int): Flow<Product> = productDao.getProductById(id)

    override fun getProductsByCategory(category: ProductCategory): Flow<List<Product>> =
        productDao.getProductsByCategory(category)

    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)

    override suspend fun insertAllProducts(products: List<Product>) {
        try {
            productDao.insertAll(products)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProduct(product: Product) = productDao.updateProduct(product)

    override suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
}