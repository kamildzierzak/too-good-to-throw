package com.example.toogoodtothrow.ui.screens.product_form.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.theme.DateFormats
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    errorMessage: Int? = null,
) {
    var showDialog by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern(DateFormats.DD_MM_YYYY)
    val pickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            .toEpochMilli()
    )

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                // https://developer.android.com/develop/ui/compose/components/datepickers
                // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                // in the Initial pass to observe events before the text field consumes them
                // in the Main pass.
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showDialog = true
                    }
                }
            },
        value = selectedDate.format(dateFormatter),
        onValueChange = {},
        label = { Text(stringResource(R.string.expiration_date_label)) },
        isError = errorMessage != null,
        readOnly = true,
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange, contentDescription = null
            )
        },
        supportingText = {
            errorMessage?.let {
                Text(
                    text = stringResource(it),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                )
            }
        })

    if (showDialog) {
        DatePickerDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            TextButton(
                onClick = {
                    pickerState.selectedDateMillis?.let { millis ->
                        val selected = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selected)
                    }
                    showDialog = false
                }) {
                Text(stringResource(id = R.string.ok))
            }
        }, dismissButton = {
            TextButton(onClick = { showDialog = false }) {
                Text(stringResource(id = R.string.cancel))
            }
        }) {
            DatePicker(state = pickerState)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldPreview() {
    TooGoodToThrowTheme {
        DatePickerField(
            selectedDate = LocalDate.now().plusDays(7), onDateSelected = {}, errorMessage = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerFieldErrorPreview() {
    TooGoodToThrowTheme {
        DatePickerField(
            selectedDate = LocalDate.now().minusDays(7),
            onDateSelected = {},
            errorMessage = R.string.error_date_invalid
        )
    }
}