package com.example.toogoodtothrow.ui.screens.product_edit

import android.net.Uri
import com.example.toogoodtothrow.data.local.ProductCategory
import java.time.LocalDate

data class ProductEditUiState(
    // product fields
    val id: Int? = null,
    val name: String = "",
    val expirationDate: LocalDate? = null,
    val category: ProductCategory? = null,
    val quantity: String = "",
    val unit: String = "",
    val imageUri: Uri? = null,

    // fields errors
    val nameError: String? = null,
    val dateError: String? = null,
    val categoryError: String? = null,
    val quantityError: String? = null,
    val unitError: String? = null,

    // other
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val saveError: String? = null
)