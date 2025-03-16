package com.example.toogoodtothrow.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreenPreviewContent(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Product List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Product"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(products) { product ->
                // Reuse your ProductListItem composable
                ProductListItem(product = product, onClick = { onProductClick(product.id) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    // Create some dummy products for preview
    val dummyProducts = listOf(
        Product(
            id = 1,
            name = "Apple",
            expirationDate = System.currentTimeMillis() + 86400000L, // 1 day from now
            category = ProductCategory.FOOD,
            quantity = "1",
            imageUrl = null,
            isDiscarded = false
        ),
        Product(
            id = 2,
            name = "Medicine",
            expirationDate = System.currentTimeMillis() + 172800000L, // 2 days from now
            category = ProductCategory.MEDICINE,
            quantity = "2",
            imageUrl = null,
            isDiscarded = false
        )
    )


    ProductListScreenPreviewContent(
        products = dummyProducts,
        onProductClick = {},
        onAddClick = {}
    )

}
