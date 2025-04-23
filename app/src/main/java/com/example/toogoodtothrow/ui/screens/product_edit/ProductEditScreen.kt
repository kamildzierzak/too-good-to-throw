package com.example.toogoodtothrow.ui.screens.product_edit

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.AppViewModelProvider
import com.example.toogoodtothrow.ui.common.TopAppBar
import com.example.toogoodtothrow.ui.screens.product_edit.components.ProductEditBody
import com.example.toogoodtothrow.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    viewModel: ProductEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    productId: Int?,
    navigateUp: () -> Unit
) {
    val uiState by viewModel.productState.collectAsState()

    // After success move to product list
    if (uiState.saveSuccess) {
        LaunchedEffect(Unit) { navigateUp() }
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
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::save) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = stringResource(R.string.save)
                )
            }
        }
    ) { innerPadding ->
        ProductEditBody(
            uiState = uiState,
            onNameChange = viewModel::onNameChange,
            onDateChange = viewModel::onDateChange,
            onCategoryChange = viewModel::onCategoryChange,
            onQuantityChange = viewModel::onQuantityChange,
            onUnitChange = viewModel::onUnitChange,
            onImageUriChange = viewModel::onImageUriChange,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Spacing.Medium)
        )
    }
}
