package com.example.toogoodtothrow.ui.screens.product_form

import com.example.toogoodtothrow.data.local.ProductCategory
import java.time.LocalDate

data class ProductFormUiState(
    val id: Int? = null,
    val name: String = "",
    val expirationDate: LocalDate = LocalDate.now(),
    val category: ProductCategory = ProductCategory.FOOD,
    val quantity: String = "",
    val unit: String = "",
    val imageUri: String? = null,
    val isDiscarded: Boolean = false,
    val isValid: Boolean = false,

    val nameError: Int? = null,
    val dateError: Int? = null,
    val quantityError: Int? = null,
    val unitError: Int? = null,
)