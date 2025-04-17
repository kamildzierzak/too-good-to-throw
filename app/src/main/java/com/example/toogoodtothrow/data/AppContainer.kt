package com.example.toogoodtothrow.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val productsRepository: IProductsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineProductsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val productsRepository: IProductsRepository by lazy {
        OfflineProductsRepository(ProductDatabase.getDatabase(context).productDao())
    }
}