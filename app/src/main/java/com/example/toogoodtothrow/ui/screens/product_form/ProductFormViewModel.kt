package com.example.toogoodtothrow.ui.screens.product_form

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.repository.IProductsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate

@OptIn(FlowPreview::class)
class ProductFormViewModel(
    private val productsRepository: IProductsRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val rawProductId: Int = savedStateHandle["productId"] ?: -1
    private val productId: Int? = if (rawProductId >= 0) rawProductId else null

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState

    private var initialFormState: ProductFormUiState? = null

    // if we are editing, load the existing product and fill the form
    init {
        productId?.let { id ->
            viewModelScope.launch {
                productsRepository.getProductById(id).collect { product ->
                    _uiState.value = product.toFormUiState()
                }
            }
        } ?: run {
            initialFormState = ProductFormUiState()
        }

        viewModelScope.launch {
            uiState.debounce(500).collect { state ->
                if (initialFormState != null && state != initialFormState) {
                    _uiState.update { validate(it) }
                }
            }
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateExpirationDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(expirationDate = date)
    }

    fun updateCategory(category: ProductCategory) {
        _uiState.value = _uiState.value.copy(category = category)
    }

    fun updateQuantity(quantity: String) {
        _uiState.value = _uiState.value.copy(quantity = quantity)
    }

    fun updateUnit(unit: String) {
        _uiState.value = _uiState.value.copy(unit = unit)
    }

    fun updateImagePath(context: Context, newUri: Uri?) {
        viewModelScope.launch {
            // If there is an existing image, delete it
            val currentImagePath = _uiState.value.imagePath
            if (currentImagePath != null) {
                deleteImageFromInternalStorage(currentImagePath)
            }

            // Save new image to internal storage and update the UI state
            val savedPath = newUri?.let { saveImageToInternalStorage(context, it) }
            _uiState.value = _uiState.value.copy(imagePath = savedPath)
        }
    }

    fun saveProduct(onFinished: () -> Unit) {
        viewModelScope.launch {
            val validatedState = validate(_uiState.value)
            _uiState.value = validatedState

            if (validatedState.isValid) {
                val product = Product(
                    id = validatedState.id ?: 0,
                    name = validatedState.name.trim(),
                    expirationDate = validatedState.expirationDate.toEpochDay(),
                    category = validatedState.category,
                    quantity = validatedState.quantity.toIntOrNull(),
                    unit = validatedState.unit.ifBlank { null },
                    imagePath = validatedState.imagePath,
                    isDiscarded = validatedState.isDiscarded
                )

                if (validatedState.id != null) {
                    productsRepository.updateProduct(product)
                } else {
                    productsRepository.insertProduct(product)
                }

                onFinished()
            }
        }
    }

    private fun validate(state: ProductFormUiState): ProductFormUiState {
        val nameOk = state.name.isNotBlank()
        val dateOk = !state.expirationDate.isBefore(LocalDate.now())
        val quantityOk = state.quantity.isEmpty() || state.quantity.toIntOrNull() != null
        val unitOk = state.quantity.isEmpty() || state.unit.isNotBlank()

        return state.copy(
            isValid = nameOk && dateOk && quantityOk && unitOk,
            nameError = if (!nameOk) R.string.error_name_required else null,
            dateError = if (!dateOk) R.string.error_date_invalid else null,
            quantityError = if (!quantityOk) R.string.error_quantity_invalid else null,
            unitError = if (!unitOk) R.string.error_unit_required else null
        )
    }

    private fun saveImageToInternalStorage(context: Context, imageUri: Uri): String? {
        return try {
            // Read bitmap from URI
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                // For API level 28 and below, use deprecated method :(
                // 'getBitmap(ContentResolver!, Uri!): Bitmap!' is deprecated. Deprecated in Java
                MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }

            // Create file in internal storage
            val directory = File(context.filesDir, "images")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val fileName = "img_${System.currentTimeMillis()}.jpg"
            val file = File(directory, fileName)

            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
            outputStream.flush()
            outputStream.close()

            file.absolutePath  // Zwracamy ścieżkę
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun deleteImageFromInternalStorage(imagePath: String?) {
        imagePath?.let {
            val file = File(it)
            if (file.exists()) {
                file.delete()
            }
        }
    }

//    private fun ProductFormUiState.validate(): ProductFormUiState {
//        val nameOk = name.isNotBlank()
//        val dateOk = !expirationDate.isBefore(LocalDate.now())
//        val quantityOk = quantity.isEmpty() || quantity.toIntOrNull() != null
//        val unitOk = quantity.isEmpty() || unit.isNotBlank()
//
//        return copy(
//            isValid = nameOk && dateOk && quantityOk && unitOk,
//            nameError = if (!nameOk) R.string.error_name_required else null,
//            dateError = if (!dateOk) R.string.error_date_invalid else null,
//            quantityError = if (!quantityOk) R.string.error_quantity_invalid else null,
//            unitError = if (!unitOk) R.string.error_unit_required else null
//        )
//    }
}

private fun Product.toFormUiState() = ProductFormUiState(
    id = id,
    name = name,
    category = category,
    expirationDate = LocalDate.ofEpochDay(expirationDate),
    quantity = quantity?.toString() ?: "",
    unit = unit ?: "",
    imagePath = imagePath,
    isDiscarded = isDiscarded,
    isValid = true
)
