package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.theme.Spacing

@Composable
fun ProductFilters(
    selectedCategory: ProductCategory?,
    showOnlyValid: Boolean,
    onCategorySelected: (ProductCategory?) -> Unit,
    onToggleValidOnly: (Boolean) -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            CategoryChip(
                label = stringResource(R.string.all),
                selected = selectedCategory == null,
                onClick = {
                    onCategorySelected(null)
                })
            ProductCategory.entries.forEach { category ->
                CategoryChip(
                    label = category.toPolish(),
                    selected = category == selectedCategory,
                    onClick = {
                        onCategorySelected(category)
                    })
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.only_valid))
            Switch(checked = showOnlyValid, onCheckedChange = onToggleValidOnly)
        }
    }
}

@Preview
@Composable
fun ProductFiltersPreview() {
    ProductFilters(
        selectedCategory = ProductCategory.FOOD,
        showOnlyValid = false,
        onCategorySelected = {},
        onToggleValidOnly = {}
    )
}