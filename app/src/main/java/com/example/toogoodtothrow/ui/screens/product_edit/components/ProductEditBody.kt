package com.example.toogoodtothrow.ui.screens.product_edit.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.ui.screens.product_edit.ProductEditUiState
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.LocalDate

@Composable
fun ProductEditBody(
    modifier: Modifier = Modifier,
    uiState: ProductEditUiState,
    onNameChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onCategoryChange: (ProductCategory) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUnitChange: (String) -> Unit,
    onImageUriChange: (Uri?) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.Small),
            value = uiState.name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.name_label)) },
            isError = uiState.nameError != null,
            supportingText = {
                uiState.nameError?.let {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = true,
        )

        Spacer(Modifier.height(Spacing.Small))

        DatePickerField(
            label = stringResource(R.string.expiration_date_label),
            selectedDate = uiState.expirationDate,
            onDateSelected = onDateChange,
            error = uiState.dateError
        )

        Spacer(Modifier.height(Spacing.Small))

        CategoryDropdown(
            label = stringResource(R.string.category_label),
            selected = uiState.category,
            onSelect = onCategoryChange,
            error = uiState.categoryError
        )

        Spacer(Modifier.height(Spacing.Small))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.quantity,
                onValueChange = onQuantityChange,
                label = { Text(stringResource(R.string.quantity_label)) },
                isError = uiState.quantityError != null,
                supportingText = {
                    uiState.quantityError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = uiState.unit,
                onValueChange = onUnitChange,
                label = { Text(stringResource(R.string.unit_label)) },
                isError = uiState.unitError != null,
                supportingText = {
                    uiState.unitError?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true
            )
        }

        Spacer(Modifier.height(Spacing.Small))

        ImagePickerField(
            imageUri = uiState.imageUri,
            onImageSelected = onImageUriChange
        )

        Spacer(Modifier.height(Spacing.Medium))

        if (uiState.isSaving) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        uiState.saveError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductEditBodyPreview() {
    TooGoodToThrowTheme {
        val dummyState = ProductEditUiState(
            id = 1,
            name = "Mleko 2%",
            expirationDate = LocalDate.now().plusDays(3),
            category = ProductCategory.FOOD,
            quantity = "1",
            unit = "L",
            imageUri = null,

            nameError = null,
            dateError = null,
            categoryError = null,
            quantityError = null,
            unitError = null,

            isSaving = false,
            saveSuccess = false,
            saveError = null
        )

        ProductEditBody(
            uiState = dummyState,
            onNameChange = {},
            onDateChange = {},
            onCategoryChange = {},
            onQuantityChange = {},
            onUnitChange = {},
            onImageUriChange = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.Medium)
        )
    }
}
