package com.example.toogoodtothrow.ui.screens.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier
) {
    val isOneDayBeforeExpiry = product.isOneDayBeforeExpiry
    val isExpired = product.isExpired

    val cardColor = product.cardColor(MaterialTheme.colorScheme)
    val textColor = product.textColor(MaterialTheme.colorScheme)
    val formattedDate = product.formattedDate()

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Spacing.ExtraSmall),
        colors = CardDefaults.cardColors(cardColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = product.imageUri,
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.placeholder)
                )
            }

            Column(
                modifier = Modifier
                    .padding(Spacing.Small)
                    .weight(1.5f),
                verticalArrangement = Arrangement.spacedBy(Spacing.ExtraSmall)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = textColor
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = product.category.toPolish(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        color = textColor
                    )

                    if (product.quantity != null && product.unit != null) {
                        Text(
                            text = "${product.quantity} ${product.unit}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.End,
                            color = textColor
                        )
                    }
                }

                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = textColor
                )

                if (isExpired) {
                    Text(
                        text = "Przeterminowany",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Spacing.Small)
                    )
                } else if (isOneDayBeforeExpiry) {
                    Text(
                        text = "Za 1 dzień",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Spacing.Small)
                    )
                } else {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Spacing.Small)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    TooGoodToThrowTheme {
        ProductCard(
            previewProducts.first()
        )
    }
}