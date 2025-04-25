package com.example.toogoodtothrow.ui.screens.product_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.repository.IProductsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProductFormViewModel(
    private val productsRepository: IProductsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val rawProductId: Int = savedStateHandle["productId"] ?: -1
    private val productId: Int? = if (rawProductId >= 0) rawProductId else null

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState

    // if we are editing, load the existing product and fill the form
    init {
        productId?.let { id ->
            viewModelScope.launch {
                productsRepository.getProductById(id).collect { product ->
                    _uiState.value = product.toFormUiState()
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name).validate()
    }

    fun updateExpirationDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(expirationDate = date).validate()
    }

    fun updateCategory(category: ProductCategory) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun updateQuantity(quantity: String) {
        _uiState.value = _uiState.value.copy(quantity = quantity).validate()
    }

    fun updateUnit(unit: String) {
        _uiState.value = _uiState.value.copy(unit = unit).validate()
    }

    fun updateImageUri(uri: String?) {
        _uiState.value = _uiState.value.copy(imageUri = uri)
    }

    fun saveProduct(onFinished: () -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val product = Product(
                id = state.id ?: 0,
                name = state.name.trim(),
                expirationDate = state.expirationDate.toEpochDay(),
                category = state.category,
                quantity = state.quantity.toIntOrNull(),
                unit = state.unit.ifBlank { null },
                imageUri = state.imageUri,
                isDiscarded = state.isDiscarded
            )

            if (state.id != null) {
                productsRepository.updateProduct(product)
            } else {
                productsRepository.insertProduct(product)
            }

            onFinished()
        }
    }

    private fun ProductFormUiState.validate(): ProductFormUiState {
        val nameOk = name.isNotBlank()
        val dateOk = !expirationDate.isBefore(LocalDate.now())
        val quantityOk = quantity.isEmpty() || quantity.toIntOrNull() != null
        val unitOk = quantity.isEmpty() || unit.isNotBlank()

        return copy(
            isValid = nameOk && dateOk && quantityOk && unitOk,
            nameError = if (!nameOk) R.string.error_name_required else null,
            dateError = if (!dateOk) R.string.error_date_invalid else null,
            quantityError = if (!quantityOk) R.string.error_quantity_invalid else null,
            unitError = if (!unitOk) R.string.error_unit_required else null
        )
    }
}

private fun Product.toFormUiState() = ProductFormUiState(
    id = id,
    name = name,
    category = category,
    expirationDate = LocalDate.ofEpochDay(expirationDate),
    quantity = quantity?.toString() ?: "",
    unit = unit ?: "",
    imageUri = imageUri,
    isDiscarded = isDiscarded,
    isValid = true
)
