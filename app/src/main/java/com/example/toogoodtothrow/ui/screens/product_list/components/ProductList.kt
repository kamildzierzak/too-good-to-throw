package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.ui.common.previewProducts
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
    onProductLongClick: (Product) -> Unit,
    contentPadding: PaddingValues,
    listState: LazyListState
) {
    val haptics = LocalHapticFeedback.current

    LazyColumn(
        modifier = modifier
            .padding(all = Spacing.Small),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        state = listState
    ) {
        items(items = productList, key = { it.id }) { item ->
            ProductCard(
                product = item,
                modifier = Modifier
                    .combinedClickable(
                        onClick = { onProductClick(item) },
                        onLongClick = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            onProductLongClick(item)
                        }
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductListPreview() {
    TooGoodToThrowTheme {
        ProductList(
            productList = previewProducts,
            onProductClick = {},
            onProductLongClick = {},
            contentPadding = PaddingValues(Spacing.Small),
            listState = LazyListState()
        )
    }
}

