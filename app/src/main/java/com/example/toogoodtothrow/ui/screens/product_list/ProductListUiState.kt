package com.example.toogoodtothrow.ui.screens.product_list

import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.isExpired

data class ProductListUiState(
    val all: List<Product> = emptyList(),
    val category: ProductCategory? = null,
    val onlyValid: Boolean = true
) {
    val visibleProducts: List<Product>
        get() = all
            .asSequence()
            .filter { category == null || it.category == category }
            .filter { !onlyValid || !it.isExpired }
            .sortedBy { it.expirationDate }
            .toList()
}