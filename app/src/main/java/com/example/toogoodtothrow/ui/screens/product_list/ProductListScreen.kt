package com.example.toogoodtothrow.ui.screens.product_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.launch
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
    viewModel: ProductListViewModel = viewModel<ProductListViewModel>(factory = AppViewModelProvider.Factory)
) {
    val productListState by viewModel.productListState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val showOnlyValid by viewModel.showOnlyValid.collectAsState()
    var productToModify by remember { mutableStateOf<Product?>(null) }
    var showDialog by remember { mutableStateOf(false) }

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
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()

        ProductListBody(
            productList = productListState.productList,
            onProductClick = { product ->
                if (!product.isDiscarded && !LocalDate.now().isAfter(LocalDate.ofEpochDay(product.expirationDate))) {
                    navigateToProductUpdate(product.id)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Nie można edytować przeterminowanego lub wyrzuconego produktu.")
                    }
                }},
            onProductLongClick = {
                productToModify = it
                showDialog = true
            },
            selectedCategory = selectedCategory,
            showOnlyValid = showOnlyValid,
            onCategorySelected = viewModel::setSelectedCategory,
            onToggleValidOnly = viewModel::setShowOnlyValid,
            contentPadding = innerPadding,
            modifier = modifier.padding(top = innerPadding.calculateTopPadding())
        )

        if (showDialog && productToModify != null) {
            val isExpired = LocalDate.now().isAfter(LocalDate.ofEpochDay(productToModify!!.expirationDate))
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    productToModify = null
                },
                title = {
                    Text(text = "Czy na pewno?")
                },
                text = {
                    Text(
                        if (isExpired)
                            "Produkt jest przeterminowany. Oznaczyć jako wyrzucony?"
                        else
                            "Czy na pewno chcesz usunąć ten produkt?"
                    )
                },
                confirmButton = {
                    Text(
                        text = "Tak",
                        modifier = Modifier.clickable {
                            if (isExpired) {
                                viewModel.markAsDiscarded(productToModify!!)
                            } else {
                                viewModel.deleteProduct(productToModify!!)
                            }
                            showDialog = false
                            productToModify = null
                        }
                    )
                },
                dismissButton = {
                    Text(
                        text = "Nie",
                        modifier = Modifier.clickable {
                            showDialog = false
                            productToModify = null
                        }
                    )
                }
            )
        }

    }
}

@Composable
private fun ProductListBody(
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
            text = "Liczba produktów: ${productList.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
        if (productList.isEmpty()) {
            Text(
                text = "Brak wyników",
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
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun ProductFilters(
    selectedCategory: ProductCategory?,
    showOnlyValid: Boolean,
    onCategorySelected: (ProductCategory?) -> Unit,
    onToggleValidOnly: (Boolean) -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(label = "Wszystkie", selected = selectedCategory == null) {
                onCategorySelected(null)
            }
            ProductCategory.entries.forEach { category ->
                FilterChip(label = category.toPolish(), selected = category == selectedCategory) {
                    onCategorySelected(category)
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Tylko ważne:")
            Switch(checked = showOnlyValid, onCheckedChange = onToggleValidOnly)
        }
    }
}

@Composable
fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val color =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    Text(
        text = label,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        color = color
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductList(
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
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onProductClick(item)},
                        onLongClick = { onProductLongClick(item)}
                    )
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
    val isOneDayBeforeExpiry =
        LocalDate.now().plusDays(1).isEqual(LocalDate.ofEpochDay(product.expirationDate))
    val isExpired = LocalDate.now().isAfter(LocalDate.ofEpochDay(product.expirationDate))

    val cardColor = when {
        product.isDiscarded -> Color(0xFF424342)
        isExpired -> Color(0xFFEA3546)
        isOneDayBeforeExpiry -> Color(0xFFF86624)
        else -> Color(0xFF06d6a0)
    }

    val textColor = when {
        product.isDiscarded -> Color.Gray
        isExpired -> Color.White
        isOneDayBeforeExpiry -> Color.DarkGray
        else -> Color.Black
    }


    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = product.imageUri,
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.cat),
                    error = painterResource(id = R.drawable.cat)
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1.5f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = textColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = product.category.toPolish(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        color = textColor
                    )

                    if (product.quantity != null && product.unit != null) {
                        Text(
                            text = "${product.quantity} ${product.unit}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End,
                            color = textColor
                        )
                    }
                }

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = textColor
                )

                if (isExpired) {
                    Text(
                        text = "Przeterminowany",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                } else if (isOneDayBeforeExpiry) {
                    Text(
                        text = "Za 1 dzień",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                } else {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
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
        expirationDate = LocalDate.now().minusDays(2).toEpochDay(),
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
        expirationDate = LocalDate.now().plusDays(1).toEpochDay(),
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
        expirationDate = LocalDate.now().minusDays(10).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 10,
        unit = "szt",
        isExpired = false,
        isDiscarded = true,
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
