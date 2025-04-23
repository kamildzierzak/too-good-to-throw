package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.listOfExampleProducts
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ProductListBody(
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
    onProductLongClick: (Product) -> Unit,
    selectedCategory: ProductCategory?,
    showOnlyValid: Boolean,
    onCategorySelected: (ProductCategory?) -> Unit,
    onToggleValidOnly: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        ProductFilters(
            selectedCategory = selectedCategory,
            showOnlyValid = showOnlyValid,
            onCategorySelected = onCategorySelected,
            onToggleValidOnly = onToggleValidOnly
        )
        Text(
            text = stringResource(R.string.products_count, productList.size),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(Spacing.Small)
        )
        if (productList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_results),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxWidth(),
            )
        } else {
            ProductList(
                productList = productList,
                onProductClick = onProductClick,
                onProductLongClick = onProductLongClick,
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = Spacing.Small)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    TooGoodToThrowTheme {
        ProductListBody(
            productList = listOfExampleProducts,
            onProductClick = {},
            onProductLongClick = {},
            selectedCategory = null,
            showOnlyValid = false,
            onCategorySelected = {},
            onToggleValidOnly = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyProductListScreenPreview() {
    TooGoodToThrowTheme {
        ProductListBody(
            productList = listOf(),
            onProductClick = {},
            onProductLongClick = {},
            selectedCategory = null,
            showOnlyValid = false,
            onCategorySelected = {},
            onToggleValidOnly = {}
        )
    }
}