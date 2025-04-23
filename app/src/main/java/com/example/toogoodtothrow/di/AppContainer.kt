package com.example.toogoodtothrow.di

import android.content.Context
import com.example.toogoodtothrow.data.local.ProductDatabase
import com.example.toogoodtothrow.data.repository.IProductsRepository
import com.example.toogoodtothrow.data.repository.ProductsRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val productsRepository: IProductsRepository
}

/**
 * [AppContainer] implementation that provides instance of [ProductsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val productsRepository: IProductsRepository by lazy {
        ProductsRepository(ProductDatabase.getDatabase(context).productDao())
    }
}