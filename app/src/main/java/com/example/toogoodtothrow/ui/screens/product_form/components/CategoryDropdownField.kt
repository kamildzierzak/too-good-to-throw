package com.example.toogoodtothrow.ui.screens.product_form.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdownField(
    modifier: Modifier = Modifier,
    selectedCategory: ProductCategory,
    onCategoryChange: (ProductCategory) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selectedCategory.toPolish(),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.category_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            singleLine = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ProductCategory.entries.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.toPolish()) },
                    onClick = {
                        onCategoryChange(category)
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
        CategoryDropdownField(
            selectedCategory = ProductCategory.FOOD,
            onCategoryChange = {}
        )
    }
}