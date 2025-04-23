package com.example.toogoodtothrow.ui.screens.product_edit

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.repository.IProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProductEditViewModel(
    private val productsRepository: IProductsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int? = savedStateHandle.get<Int>("productId")

    private val _ui = MutableStateFlow(ProductEditUiState(id = productId))
    val productState: StateFlow<ProductEditUiState> = _ui

    init {
        if (productId != null) {
            viewModelScope.launch {
                val product = productsRepository.getProductById(productId).first()
                _ui.update {
                    it.copy(
                        name = product.name,
                        expirationDate = LocalDate.ofEpochDay(product.expirationDate),
                        category = product.category,
                        quantity = product.quantity?.toString() ?: "",
                        unit = product.unit ?: "",
                        imageUri = product.imageUri?.let(Uri::parse)
                    )
                }
            }
        }
    }

    fun onNameChange(new: String) = _ui.update {
        it.copy(name = new, nameError = null)
    }

    fun onDateChange(date: LocalDate) = _ui.update {
        it.copy(expirationDate = date, dateError = null)
    }

    fun onCategoryChange(cat: ProductCategory) = _ui.update { it.copy(category = cat) }

    fun onQuantityChange(q: String) = _ui.update {
        it.copy(quantity = q, quantityError = null)
    }

    fun onUnitChange(u: String) = _ui.update {
        it.copy(unit = u, unitError = null)
    }

    fun onImageUriChange(uri: Uri?) = _ui.update { it.copy(imageUri = uri) }

    fun save() {
        val state = _ui.value

        var hasError = false
        if (state.name.isBlank()) {
            _ui.update { it.copy(nameError = "Nazwa nie może być pusta") }
            hasError = true
        }
        if (state.expirationDate == null || state.expirationDate.isBefore(LocalDate.now())) {
            _ui.update { it.copy(dateError = "Wybierz przyszłą datę") }
            hasError = true
        }
        if (state.quantity.isNotBlank() && state.quantity.toIntOrNull() == null) {
            _ui.update { it.copy(quantityError = "Ilość musi być liczbą") }
            hasError = true
        }
        if (state.quantity.isNotBlank() && state.unit.isBlank()) {
            _ui.update { it.copy(unitError = "Podaj jednostkę") }
            hasError = true
        }
        if (state.category == null) {
            _ui.update { it.copy(categoryError = "Proszę wybrać kategorię") }
            hasError = true
        }
        if (hasError) return

        val product = Product(
            id = state.id ?: 0,
            name = state.name.trim(),
            expirationDate = state.expirationDate!!.toEpochDay(),
            category = state.category ?: ProductCategory.OTHER,
            quantity = state.quantity.toIntOrNull(),
            unit = state.unit.ifBlank { null },
            isDiscarded = false,
            imageUri = state.imageUri?.toString()
        )

        viewModelScope.launch {
            _ui.update { it.copy(isSaving = true, saveError = null) }
            try {
                if (state.id == null) productsRepository.insertProduct(product)
                else productsRepository.updateProduct(product)
                _ui.update { it.copy(isSaving = false, saveSuccess = true) }
            } catch (e: Exception) {
                _ui.update { it.copy(isSaving = false, saveError = e.localizedMessage) }
            }
        }
    }
}
