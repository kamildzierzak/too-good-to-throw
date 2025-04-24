package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.ui.common.previewProducts
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ProductListBody(
    modifier: Modifier = Modifier,
    productList: List<Product>,
    selectedCategory: ProductCategory?,
    onCategorySelected: (ProductCategory?) -> Unit,
    showOnlyValid: Boolean,
    onToggleValidOnly: (Boolean) -> Unit,
    onProductClick: (Product) -> Unit,
    onProductLongClick: (Product) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val emptyLabel = stringResource(R.string.no_results)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProductFilters(
            selectedCategory = selectedCategory,
            showOnlyValid = showOnlyValid,
            onCategorySelected = onCategorySelected,
            onToggleValidOnly = onToggleValidOnly
        )

        ProductCounter(
            modifier = Modifier.padding(vertical = Spacing.ExtraSmall),
            total = productList.size
        )

        if (productList.isEmpty()) {
            ProductListEmptyState(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(top = Spacing.Medium),
                label = emptyLabel
            )
        } else {
            ProductList(
                modifier = Modifier,
                productList = productList,
                onProductClick = onProductClick,
                onProductLongClick = onProductLongClick,
                contentPadding = contentPadding
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductListScreenPreview() {
    TooGoodToThrowTheme {
        ProductListBody(
            productList = previewProducts,
            selectedCategory = null,
            onCategorySelected = {},
            showOnlyValid = false,
            onToggleValidOnly = {},
            onProductClick = {},
            onProductLongClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyProductListScreenPreview() {
    TooGoodToThrowTheme {
        ProductListBody(
            productList = listOf(),
            selectedCategory = null,
            onCategorySelected = {},
            showOnlyValid = false,
            onToggleValidOnly = {},
            onProductClick = {},
            onProductLongClick = {},
        )
    }
}