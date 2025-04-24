package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ProductListEmptyState(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = label },
        text = label,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductListEmptyStatePreview() {
    TooGoodToThrowTheme {
        ProductListEmptyState(label = "Brak wyników")
    }
}
