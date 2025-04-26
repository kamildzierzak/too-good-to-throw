package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.cardColor
import com.example.toogoodtothrow.data.local.formattedDate
import com.example.toogoodtothrow.data.local.isExpired
import com.example.toogoodtothrow.data.local.isOneDayBeforeExpiry
import com.example.toogoodtothrow.data.local.textColor
import com.example.toogoodtothrow.data.local.toPolish
import com.example.toogoodtothrow.ui.common.previewProducts
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.io.File

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product
) {
    val cardColor = product.cardColor(MaterialTheme.colorScheme)
    val textColor = product.textColor(MaterialTheme.colorScheme)
    val formattedDate = product.formattedDate()

    Card(
        modifier = modifier
            .semantics { contentDescription = product.name },
        colors = CardDefaults.cardColors(cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.ExtraSmall),
    ) {
        Row(
            modifier = Modifier.padding(Spacing.Small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .clip(shape = MaterialTheme.shapes.medium),
                model = product.imagePath?.let { File(it) } ?: R.drawable.placeholder,
                contentDescription = product.name,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = Spacing.Small)
                    .weight(2f),
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = product.category.toPolish(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor,
                        textAlign = TextAlign.End
                    )

                    if (product.quantity != null && product.unit != null) {
                        Text(
                            text = "${product.quantity} ${product.unit}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor,
                            textAlign = TextAlign.End
                        )
                    }
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = formattedDate,
                    style = MaterialTheme.typography.titleLarge,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                val productStatus = when {
                    product.isDiscarded -> R.string.status_discarded
                    product.isExpired -> R.string.status_expired
                    product.isOneDayBeforeExpiry -> R.string.status_one_day
                    else -> R.string.status_ok
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(productStatus),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = textColor,
                    textAlign = TextAlign.Center
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CardFreshPreview() {
    TooGoodToThrowTheme {
        ProductCard(product = previewProducts[0])
    }
}

@Preview(showBackground = true)
@Composable
private fun CardOneDayPreview() {
    TooGoodToThrowTheme {
        ProductCard(product = previewProducts[1])
    }
}

@Preview(showBackground = true)
@Composable
private fun CardExpiredPreview() {
    TooGoodToThrowTheme {
        ProductCard(product = previewProducts[2])
    }
}

@Preview(showBackground = true)
@Composable
private fun CardDiscardedPreview() {
    TooGoodToThrowTheme {
        ProductCard(product = previewProducts[3])
    }
}