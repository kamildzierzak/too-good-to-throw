package com.example.toogoodtothrow.ui.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.data.IProductsRepository
import com.example.toogoodtothrow.data.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProductListViewModel(productsRepository: IProductsRepository): ViewModel() {
    val productListState: StateFlow<ProductListState> =
        productsRepository.getAllProducts().map { ProductListState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProductListState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ProductListState(val productList: List<Product> = listOf())