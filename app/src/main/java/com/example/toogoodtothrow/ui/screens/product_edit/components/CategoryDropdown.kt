package com.example.toogoodtothrow.ui.screens.product_edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    modifier: Modifier = Modifier,
    label: String,
    selected: ProductCategory?,
    onSelect: (ProductCategory) -> Unit,
    error: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    val entries = ProductCategory.entries.map { it to it.toPolish() }
    val selectedItemText =
        selected?.toPolish() ?: stringResource(R.string.select_category_placeholder)

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(
                    type = MenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .semantics {
                    contentDescription = "$label: $selectedItemText"
                },
            value = selectedItemText,
            onValueChange = { /* read-only */ },
            readOnly = true,
            label = { Text(label) },
            isError = error != null,
            supportingText = {
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            singleLine = true,
        )
        ExposedDropdownMenu(
            modifier = Modifier.heightIn(max = 250.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            entries.forEach { (category, categoryLabel) ->
                DropdownMenuItem(
                    text = { Text(categoryLabel) },
                    onClick = {
                        onSelect(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownPreview() {
    TooGoodToThrowTheme {
        var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
        CategoryDropdown(
            modifier = Modifier.padding(16.dp),
            label = stringResource(R.string.category_label),
            selected = selectedCategory,
            onSelect = { selectedCategory = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownSelectedPreview() {
    TooGoodToThrowTheme {
        CategoryDropdown(
            label = stringResource(R.string.category_label),
            selected = ProductCategory.MEDICINE,
            onSelect = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryDropdownErrorPreview() {
    TooGoodToThrowTheme {
        var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
        CategoryDropdown(
            modifier = Modifier.padding(16.dp),
            label = stringResource(R.string.category_label),
            selected = selectedCategory,
            onSelect = { selectedCategory = it },
            error = stringResource(R.string.category_required_error)
        )
    }
}
