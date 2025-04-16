package com.example.toogoodtothrow.ui.screens.product_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.TopAppBar
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductCategory
import com.example.toogoodtothrow.data.toPolish
import com.example.toogoodtothrow.ui.AppViewModelProvider
import com.example.toogoodtothrow.ui.navigation.NavigationDestination
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ProductListDestination : NavigationDestination {
    override val route = "product_list"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navigateToProductDetail: () -> Unit,
    navigateToProductUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val productListState by viewModel.productListState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(ProductListDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductDetail,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_product_title)
                )
            }
        },
    ) { innerPadding ->
        ProductListBody(
            productList = productListState.productList,
            onProductClick = navigateToProductUpdate,
            contentPadding = innerPadding,
            modifier = modifier
        )
    }
}

@Composable
private fun ProductListBody(
    productList: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (productList.isEmpty()) {
            Text(
                text = "Brak",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            ProductList(
                productList = productList,
                onProductClick = { onProductClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun ProductList(
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
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
                    .padding(8.dp)
                    .clickable { onProductClick(item) }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    val formattedDate = LocalDate.ofEpochDay(product.expirationDate)
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(Color(0xFF57cc99))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .weight(1f),
                model = product.imageUri,
                contentDescription = product.name,
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.cat),
                error = painterResource(id = R.drawable.cat)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1.75f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = product.category.toPolish(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                    )

                    if (product.quantity != null && product.unit != null) {
                        Text(
                            text = "${product.quantity} ${product.unit}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End,
                        )
                    }
                }

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    TooGoodToThrowTheme {
        ProductCard(
            product = listOfExampleProducts[0]
        )
    }
}

val listOfExampleProducts = listOf(
    Product(
        id = 1,
        name = "Żeberka",
        expirationDate = LocalDate.now().plusDays(7).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "kg",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 2,
        name = "Mleko 2%",
        expirationDate = LocalDate.now().plusDays(3).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
        unit = "L",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 3,
        name = "Jajka",
        expirationDate = LocalDate.now().plusDays(10).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 10,
        unit = "szt",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 4,
        name = "Szampon Head & Shoulders",
        expirationDate = LocalDate.now().plusMonths(12).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "butelka",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 5,
        name = "Płyn do płukania jamy ustnej",
        expirationDate = LocalDate.now().plusMonths(24).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "L",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 6,
        name = "Ser żółty",
        expirationDate = LocalDate.now().plusDays(5).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
        unit = "kg",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 7,
        name = "Jogurt naturalny",
        expirationDate = LocalDate.now().plusDays(4).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 4,
        unit = "x125g",
        isExpired = false,
        isDiscarded = false,
        imageUri = null
    )
)
