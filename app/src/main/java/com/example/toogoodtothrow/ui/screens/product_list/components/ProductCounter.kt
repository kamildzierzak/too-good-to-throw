package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ProductCounter(
    modifier: Modifier = Modifier,
    total: Int
) {
    val countLabel = stringResource(R.string.products_count_label, total)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Small, vertical = Spacing.ExtraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            modifier = Modifier
                .semantics { contentDescription = countLabel },
            text = countLabel,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCounterPreview_Empty() {
    TooGoodToThrowTheme {
        ProductCounter(total = 0)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCounterPreview_Some() {
    TooGoodToThrowTheme {
        ProductCounter(total = 42)
    }
}