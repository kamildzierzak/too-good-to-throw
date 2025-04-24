package com.example.toogoodtothrow.ui.screens.product_edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    label: String,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    error: String? = null
) {
    var showDialog by remember { mutableStateOf(false) }

    val formattedProductDate = remember { DateTimeFormatter.ofPattern("dd-MM-yyyy") }
    val expirationDateText = selectedDate?.format(formattedProductDate)
        ?: stringResource(R.string.expiration_date_placeholder)  // placeholder :contentReference[oaicite:8]{index=8}

    val pickerState = rememberDatePickerState(
        initialDisplayedMonthMillis = selectedDate
            ?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
            ?: Instant.now().toEpochMilli(),
        initialSelectedDateMillis = selectedDate
            ?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    pickerState.selectedDateMillis?.let { ms ->
                        val dt = Instant.ofEpochMilli(ms)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(dt)
                    }
                    showDialog = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.ExtraSmall)
            .clickable { showDialog = true }          // <– TU jest klucz
            .semantics { contentDescription = "$label: $expirationDateText" }
    ) {
        OutlinedTextField(
            value = expirationDateText,
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            isError = (error != null),
            label = { Text(label) },
            supportingText = {
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.select_date)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreview() {
    TooGoodToThrowTheme {
        DatePickerField(
            modifier = Modifier.padding(16.dp),
            label = stringResource(R.string.expiration_date_label),
            selectedDate = null,
            onDateSelected = {},
            error = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreviewSelected() {
    TooGoodToThrowTheme {
        DatePickerField(
            modifier = Modifier.padding(16.dp),
            label = stringResource(R.string.expiration_date_label),
            selectedDate = LocalDate.of(2025, 5, 12),
            onDateSelected = {},
            error = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreviewError() {
    TooGoodToThrowTheme {
        DatePickerField(
            modifier = Modifier.padding(16.dp),
            label = stringResource(R.string.expiration_date_label),
            selectedDate = null,
            onDateSelected = {},
            error = stringResource(R.string.expiration_date_error)
        )
    }

}