package com.example.toogoodtothrow.ui.screens.product_form

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.AppViewModelProvider
import com.example.toogoodtothrow.ui.common.TopAppBar
import com.example.toogoodtothrow.ui.screens.product_form.components.ProductFormBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductFormViewModel = viewModel(factory = AppViewModelProvider.Factory),
    productId: Int? = null,
    onSaveFinished: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateImagePath(context, uri)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(
                    if (productId == null)
                        R.string.add_product
                    else
                        R.string.edit_product
                ),
                canNavigateBack = true,
                navigateUp = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (uiState.isValid) viewModel.saveProduct(onSaveFinished) }) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save)
                )
            }
        }
    ) { innerPadding ->
        ProductFormBody(
            modifier = Modifier
                .padding(innerPadding),

            name = uiState.name,
            onNameChange = viewModel::updateName,
            nameError = uiState.nameError,

            category = uiState.category,
            onCategoryChange = viewModel::updateCategory,

            expirationDate = uiState.expirationDate,
            onDatePickClick = viewModel::updateExpirationDate,
            dateError = uiState.dateError,

            quantity = uiState.quantity,
            onQuantityChange = viewModel::updateQuantity,
            quantityError = uiState.quantityError,

            unit = uiState.unit,
            onUnitChange = viewModel::updateUnit,
            unitError = uiState.unitError,

            imageUri = uiState.imagePath,
            onPickImageClick = {
                imagePickerLauncher.launch("image/*")
            }
        )
    }
}
