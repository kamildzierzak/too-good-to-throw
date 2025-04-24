package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.isExpired
import com.example.toogoodtothrow.ui.common.previewProducts
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ConfirmDiscardOrDeleteDialog(
    modifier: Modifier = Modifier,
    product: Product,
    onConfirm: (Product) -> Unit,
    onDismiss: () -> Unit
) {
    val isExpired = product.isExpired

    val dialogTitle = stringResource(R.string.confirm_dialog_title)
    val dialogText = if (isExpired)
        stringResource(R.string.confirm_discard_text)
    else stringResource(R.string.confirm_delete_text)

    val confirmLabel = stringResource(R.string.yes)
    val confirmDesc = if (isExpired)
        stringResource(R.string.cd_discard)
    else
        stringResource(R.string.cd_delete)

    val cancelLabel = stringResource(R.string.no)
    val cancelDesc = stringResource(R.string.cd_cancel)

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                dialogTitle,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                dialogText,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                modifier = Modifier.semantics { contentDescription = confirmDesc },
                onClick = {
                    onConfirm(product)
                    onDismiss()
                }
            ) {
                Text(confirmLabel)
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.semantics {
                    contentDescription = cancelDesc
                    dismiss { onDismiss(); true }
                },
                onClick = onDismiss
            ) {
                Text(cancelLabel)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDeleteDialogPreview() {
    TooGoodToThrowTheme {
        ConfirmDiscardOrDeleteDialog(
            product = previewProducts.first(),
            onConfirm = {}, onDismiss = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ConfirmDiscardDialogPreview() {
    TooGoodToThrowTheme {
        ConfirmDiscardOrDeleteDialog(
            product = previewProducts[2].copy(isDiscarded = false),
            onConfirm = {}, onDismiss = {}
        )
    }
}