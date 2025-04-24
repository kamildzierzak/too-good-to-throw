package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    FilterChip(
        modifier = modifier
            .semantics { contentDescription = label },
        selected = selected,
        onClick = onClick,
        enabled = enabled,
        label = {
            Text(
                text = label,
                maxLines = 1,
            )
        },
        shape = MaterialTheme.shapes.small,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun FilterChipSelectedPreview() {
    TooGoodToThrowTheme {
        CategoryChip(label = "Jedzenie", selected = true, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipUnselectedPreview() {
    TooGoodToThrowTheme {
        CategoryChip(label = "Jedzenie", selected = false, onClick = {})
    }
}