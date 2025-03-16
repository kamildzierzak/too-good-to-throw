package com.example.toogoodtothrow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.domain.usecase.DeleteDiscardedProductsUseCase
import com.example.toogoodtothrow.domain.usecase.DeleteProductUseCase
import com.example.toogoodtothrow.domain.usecase.GetAllProductsUseCase
import com.example.toogoodtothrow.domain.usecase.GetProductByIdUseCase
import com.example.toogoodtothrow.domain.usecase.InsertProductUseCase
import com.example.toogoodtothrow.domain.usecase.UpdateProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val insertProductUseCase: InsertProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteDiscardedProductsUseCase: DeleteDiscardedProductsUseCase
) : ViewModel() {

    // Holds the list of products for the UI.
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            // Collects the product list from the use case and updates the state.
            getAllProductsUseCase().collect { productList ->
                _products.value = productList
            }
        }
    }

    // Returns a Flow of a single product by ID.
    fun getProductById(id: Int) = getProductByIdUseCase(id)

    // Inserts a new product.
    fun addProduct(product: Product) {
        viewModelScope.launch {
            insertProductUseCase(product)
        }
    }

    // Updates an existing product.
    fun updateProduct(product: Product) {
        viewModelScope.launch {
            updateProductUseCase(product)
        }
    }

    // Deletes a specific product.
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product)
        }
    }

    // Clears all discarded/expired products.
    fun clearExpiredProducts() {
        viewModelScope.launch {
            deleteDiscardedProductsUseCase()
        }
    }
}