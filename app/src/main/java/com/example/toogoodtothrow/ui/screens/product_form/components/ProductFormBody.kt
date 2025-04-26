package com.example.toogoodtothrow.ui.screens.product_form.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.LocalDate

@Composable
fun ProductFormBody(
    modifier: Modifier = Modifier,

    name: String,
    onNameChange: (String) -> Unit,
    nameError: Int?,

    category: ProductCategory,
    onCategoryChange: (ProductCategory) -> Unit,

    expirationDate: LocalDate,
    onDatePickClick: (LocalDate) -> Unit,
    dateError: Int?,

    quantity: String,
    onQuantityChange: (String) -> Unit,
    quantityError: Int?,

    unit: String,
    onUnitChange: (String) -> Unit,
    unitError: Int?,

    imageUri: String?,
    onPickImageClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.Medium),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.name_label)) },
            isError = nameError != null,
            singleLine = true,
            supportingText = {
                nameError?.let {
                    Text(
                        text = stringResource(it),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
        )

        DatePickerField(
            selectedDate = expirationDate,
            onDateSelected = onDatePickClick,
            errorMessage = dateError
        )

        CategoryDropdownField(
            selectedCategory = category,
            onCategoryChange = onCategoryChange
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = quantity,
                onValueChange = onQuantityChange,
                label = { Text(stringResource(R.string.quantity_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isError = quantityError != null,
                singleLine = true,
                supportingText = {
                    quantityError?.let {
                        Text(
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = unit,
                onValueChange = onUnitChange,
                label = { Text(stringResource(R.string.unit_label)) },
                isError = unitError != null,
                singleLine = true,
                supportingText = {
                    unitError?.let {
                        Text(
                            text = stringResource(it),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
            )
        }

        ImagePickerField(
            imageUri = imageUri,
            onPickImageClick = onPickImageClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductFormBodyPreview() {
    TooGoodToThrowTheme {
        ProductFormBody(
            name = "Pomidory",
            onNameChange = {},
            nameError = null,
            category = ProductCategory.FOOD,
            onCategoryChange = {},
            expirationDate = LocalDate.now().plusDays(7),
            onDatePickClick = {},
            dateError = null,
            quantity = "1",
            onQuantityChange = {},
            quantityError = null,
            unit = "kg",
            onUnitChange = {},
            unitError = null,
            imageUri = null,
            onPickImageClick = {}
        )
    }
}

