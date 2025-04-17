package com.example.toogoodtothrow.ui.screens.product_details

import androidx.lifecycle.ViewModel
import com.example.toogoodtothrow.data.IProductsRepository

class ProductDetailsViewModel(
    private val productRepository: IProductsRepository
) : ViewModel()