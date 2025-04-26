package com.example.toogoodtothrow.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.toogoodtothrow.TooGoodToThrowApplication
import com.example.toogoodtothrow.ui.screens.product_form.ProductFormViewModel
import com.example.toogoodtothrow.ui.screens.product_list.ProductListViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ProductListViewModel
        initializer {
            ProductListViewModel(
                productsRepository = application().container.productsRepository
            )
        }

        // Initializer for ProductEditViewModel
        initializer {
            ProductFormViewModel(
                productsRepository = application().container.productsRepository,
                savedStateHandle = createSavedStateHandle()
            )
        }
    }
}

/**
 * Extension function to queries for application object and returns an instance of
 */
fun CreationExtras.application(): TooGoodToThrowApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TooGoodToThrowApplication)