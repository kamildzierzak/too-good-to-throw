package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.isExpired
import com.example.toogoodtothrow.data.local.listOfExampleProducts

@Composable
fun ConfirmDiscardOrDeleteDialog(
    product: Product,
    onConfirm: (Product) -> Unit,
    onDismiss: () -> Unit
) {
    val isExpired = product.isExpired
    val title = stringResource(R.string.confirm_dialog_title)
    val text = if (isExpired) stringResource(R.string.confirm_discard_text)
    else stringResource(R.string.confirm_delete_text)
    val confirm = stringResource(R.string.yes)
    val confirmDesc = if (isExpired)
        stringResource(R.string.cd_discard)
    else
        stringResource(R.string.cd_delete)
    val cancel = stringResource(R.string.no)
    val cancelDesc = stringResource(R.string.cd_cancel)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(
                modifier = Modifier.semantics { contentDescription = confirmDesc },
                onClick = {
                    onConfirm(product)
                    onDismiss()
                }
            ) {
                Text(confirm)
            }
        },
        dismissButton = {
            TextButton(
                modifier = Modifier.semantics { contentDescription = cancelDesc },
                onClick = onDismiss
            ) {
                Text(cancel)
            }
        }
    )
}

@Preview
@Composable
fun ConfirmDiscardOrDeleteDialogPreview() {
    ConfirmDiscardOrDeleteDialog(
        listOfExampleProducts.first(),
        onConfirm = {},
        onDismiss = {}
    )
}