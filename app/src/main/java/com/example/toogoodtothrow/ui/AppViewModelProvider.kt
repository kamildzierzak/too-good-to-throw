package com.example.toogoodtothrow.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.toogoodtothrow.TooGoodToThrowApplication
import com.example.toogoodtothrow.ui.screens.product_list.ProductListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProductListViewModel(application().container.productsRepository)
        }
    }
}

fun CreationExtras.application(): TooGoodToThrowApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TooGoodToThrowApplication)