package com.example.toogoodtothrow.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toogoodtothrow.data.Product
import com.example.toogoodtothrow.data.ProductCategory
import com.example.toogoodtothrow.presentation.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreen(
    productId: Int? = null, // If null, we're adding a new product; otherwise, editing.
    viewModel: ProductViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // State holders for our fields.
    var name by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    // If editing, load the existing product and pre-fill the fields.
    if (productId != null) {
        val product by viewModel.getProductById(productId).collectAsState(initial = null)
        LaunchedEffect(product) {
            product?.let {
                name = it.name
                // For simplicity, we display the raw epoch value.
                expirationDate = it.expirationDate.toString()
                // Convert enum to string for display.
                categoryText = it.category.name
                quantity = it.quantity ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productId == null) "Add Product" else "Edit Product") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = expirationDate,
                onValueChange = { expirationDate = it },
                label = { Text("Expiration Date (epoch)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = categoryText,
                onValueChange = { categoryText = it },
                label = { Text("Category (FOOD, MEDICINE, COSMETICS)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Convert expiration date string to a Long value, or default to current time.
                    val expDate = expirationDate.toLongOrNull() ?: System.currentTimeMillis()
                    // Convert the user-entered category text into a ProductCategory enum.
                    val cat = try {
                        ProductCategory.valueOf(categoryText.uppercase())
                    } catch (e: Exception) {
                        // Default to FOOD if the conversion fails. You might want to show an error instead.
                        ProductCategory.FOOD
                    }
                    val product = Product(
                        id = productId ?: 0, // For new products, Room will auto-generate the id.
                        name = name,
                        expirationDate = expDate,
                        category = cat,
                        quantity = if (quantity.isBlank()) null else quantity,
                        imageUrl = null, // Image handling can be added later.
                        isDiscarded = false
                    )
                    if (productId == null) {
                        viewModel.addProduct(product)
                    } else {
                        viewModel.updateProduct(product)
                    }
                    onBack() // Navigate back after saving.
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Product")
            }
        }
    }
}