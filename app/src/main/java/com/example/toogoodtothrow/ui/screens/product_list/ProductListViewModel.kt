package com.example.toogoodtothrow.ui.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.repository.IProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productsRepository: IProductsRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    private val _onlyValid = MutableStateFlow<Boolean>(true)

    val productListState: StateFlow<ProductListUiState> =
        combine(
            productsRepository.getAllProducts(),
            _selectedCategory,
            _onlyValid
        ) { products, selectedCategory, onlyValid ->
            ProductListUiState(
                all = products,
                category = selectedCategory,
                onlyValid = onlyValid
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProductListUiState()
        )

    fun setSelectedCategory(category: ProductCategory?) {
        _selectedCategory.value = category
    }

    fun setShowOnlyValidProducts(onlyValid: Boolean) {
        _onlyValid.value = onlyValid
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productsRepository.deleteProduct(product)
        }
    }

    fun markProductAsDiscarded(product: Product) {
        viewModelScope.launch {
            val updated = product.copy(isDiscarded = true)
            productsRepository.updateProduct(updated)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}