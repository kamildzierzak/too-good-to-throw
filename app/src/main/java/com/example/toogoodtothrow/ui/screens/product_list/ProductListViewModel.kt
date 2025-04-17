package com.example.toogoodtothrow.ui.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.data.IProductsRepository
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProductListViewModel(private val productsRepository: IProductsRepository) : ViewModel() {
    private val _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    val selectedCategory: StateFlow<ProductCategory?> = _selectedCategory

    private val _showOnlyValid = MutableStateFlow<Boolean>(true)
    val showOnlyValid: StateFlow<Boolean> = _showOnlyValid

    val productListState: StateFlow<ProductListState> =
        combine(
            productsRepository.getAllProducts(),
            _selectedCategory,
            _showOnlyValid
        ) { products, selectedCategory, onlyValid ->
            val filteredProducts = products
                .filter { selectedCategory == null || it.category == selectedCategory }
                .filter {
                    !onlyValid || !LocalDate.now()
                        .isAfter(LocalDate.ofEpochDay(it.expirationDate))
                }
//                .sortedBy { it.expirationDate }

            ProductListState(filteredProducts)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProductListState()
        )

    fun setSelectedCategory(category: ProductCategory?) {
        _selectedCategory.value = category
    }

    fun setShowOnlyValid(onlyValid: Boolean) {
        _showOnlyValid.value = onlyValid
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productsRepository.deleteProduct(product)
        }
    }

    fun markAsDiscarded(product: Product) {
        viewModelScope.launch {
            val updated = product.copy(isDiscarded = true)
            productsRepository.updateProduct(updated)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ProductListState(val productList: List<Product> = listOf())