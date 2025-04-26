package com.example.toogoodtothrow.ui.screens.product_list

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.isExpired
import com.example.toogoodtothrow.ui.AppViewModelProvider
import com.example.toogoodtothrow.ui.common.TopAppBar
import com.example.toogoodtothrow.ui.screens.product_list.components.ConfirmDiscardOrDeleteDialog
import com.example.toogoodtothrow.ui.screens.product_list.components.ProductListBody
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel<ProductListViewModel>(factory = AppViewModelProvider.Factory),
    navigateToProductDetail: () -> Unit,
    navigateToProductEdit: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var productToModify by remember { mutableStateOf<Product?>(null) }

    val addProductDesc = stringResource(R.string.add_product)
    val snackbarText = stringResource(R.string.cannot_edit_expired)

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.app_name),
                canNavigateBack = false
            )
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                hostState = snackbarHostState
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductDetail,
                shape = MaterialTheme.shapes.medium,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(
                        end = WindowInsets
                            .safeDrawing
                            .asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
                    .semantics { contentDescription = addProductDesc }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        ProductListBody(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding()),
            productList = uiState.visibleProducts,
            selectedCategory = uiState.category,
            onCategorySelected = viewModel::setSelectedCategory,
            showOnlyValid = uiState.onlyValid,
            onToggleValidOnly = { viewModel.toggleValidOnly() },
            onProductClick = { product ->
                val expired = product.isExpired
                if (!product.isDiscarded && !expired) {
                    navigateToProductEdit(product.id)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = snackbarText
                        )
                    }
                }
            },
            onProductLongClick = {
                productToModify = it
            }
        )

        productToModify?.let { product ->
            if (!product.isDiscarded) {
                ConfirmDiscardOrDeleteDialog(
                    product = product,
                    onConfirm = { p ->
                        if (p.isExpired) viewModel.markProductAsDiscarded(p)
                        else viewModel.deleteProduct(p)
                    },
                    onDismiss = {
                        productToModify = null
                    }
                )
            }
        }
    }
}





