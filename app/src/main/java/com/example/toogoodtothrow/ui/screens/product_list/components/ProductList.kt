package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.ui.common.previewProducts
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductList(
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
    onProductLongClick: (Product) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = productList, key = { it.id }) { item ->
            ProductCard(
                product = item,
                modifier = Modifier
                    .padding(Spacing.Small)
                    .combinedClickable(
                        onClick = { onProductClick(item) },
                        onLongClick = { onProductLongClick(item) }
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    TooGoodToThrowTheme {
        ProductList(
            productList = previewProducts,
            onProductClick = {},
            onProductLongClick = {},
            contentPadding = PaddingValues(Spacing.Small)
        )
    }
}

