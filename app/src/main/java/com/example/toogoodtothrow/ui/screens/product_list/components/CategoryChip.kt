package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
            )
        },
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