package com.example.toogoodtothrow.ui.screens.product_form.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.ProductCategory
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.theme.DateFormats
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProductFormBody(
    modifier: Modifier = Modifier,

    name: String,
    onNameChange: (String) -> Unit,
    nameError: Int?,

    category: ProductCategory,
    onCategoryChange: (ProductCategory) -> Unit,

    expirationDate: LocalDate,
    onDatePickClick: () -> Unit,
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

        Button(onClick = onDatePickClick) {
            Text(text = stringResource(R.string.expiration_date_label))
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = expirationDate.format(DateTimeFormatter.ofPattern(DateFormats.DD_MM_YYYY)),
            onValueChange = {},
            label = { Text(stringResource(R.string.expiration_date_label)) },
            isError = dateError != null,
            singleLine = true,
            supportingText = {
                dateError?.let {
                    Text(
                        text = stringResource(it),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        )

        Text(stringResource(R.string.category_label))
        ProductCategory.entries.forEach { cat ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = category == cat,
                    onClick = { onCategoryChange(cat) }
                )
                Text(text = cat.toPolish())
            }
        }

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

        Button(
            onClick = onPickImageClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.image_label))
        }
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder)
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

