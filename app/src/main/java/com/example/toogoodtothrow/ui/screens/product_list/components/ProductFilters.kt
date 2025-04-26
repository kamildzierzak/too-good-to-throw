package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductFilters(
    modifier: Modifier = Modifier,
    selectedCategory: ProductCategory?,
    showOnlyValid: Boolean,
    onCategorySelected: (ProductCategory?) -> Unit,
    onToggleValidOnly: (Boolean) -> Unit
) {
    val allLabel = stringResource(R.string.all)
    val onlyValidLabel = stringResource(R.string.only_valid_products_label)

    Column(
        modifier = modifier.padding(vertical = Spacing.Small)
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.ExtraSmall, horizontal = Spacing.Small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            CategoryChip(
                label = allLabel,
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) })

            ProductCategory.entries.forEach { category ->
                CategoryChip(
                    label = category.toPolish(),
                    selected = category == selectedCategory,
                    onClick = { onCategorySelected(category) })
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.Small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier.padding(end = Spacing.Small),
                text = onlyValidLabel,
                style = MaterialTheme.typography.labelLarge
            )
            Switch(
                modifier = Modifier.semantics { contentDescription = onlyValidLabel },
                checked = showOnlyValid,
                onCheckedChange = onToggleValidOnly,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.secondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                    uncheckedThumbColor = MaterialTheme.colorScheme.surfaceVariant,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductFiltersPreview_All() {
    TooGoodToThrowTheme {
        ProductFilters(
            selectedCategory = null,
            showOnlyValid = false,
            onCategorySelected = {},
            onToggleValidOnly = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductFiltersPreview_FoodOnlyValid() {
    TooGoodToThrowTheme {
        ProductFilters(
            selectedCategory = ProductCategory.FOOD,
            showOnlyValid = true,
            onCategorySelected = {},
            onToggleValidOnly = {})
    }
}