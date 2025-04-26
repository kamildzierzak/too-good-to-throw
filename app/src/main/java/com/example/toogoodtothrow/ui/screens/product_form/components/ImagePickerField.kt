package com.example.toogoodtothrow.ui.screens.product_form.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme
import java.io.File

@Composable
fun ImagePickerField(
    modifier: Modifier = Modifier,
    imageUri: String?,
    onPickImageClick: () -> Unit
) {

    val labelText = if (imageUri == null) {
        stringResource(R.string.add_image)
    } else {
        stringResource(R.string.edit_image)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.Small)
    ) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onPickImageClick() }
        ) {
            Icon(
                imageVector = if (imageUri == null) Icons.Default.Add else Icons.Default.Edit,
                contentDescription = stringResource(R.string.image_label),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(Spacing.Small))
            Text(
                text = labelText,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    if (imageUri != null) {
        AsyncImage(
            model = File(imageUri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePickerFieldPreview_Empty() {
    TooGoodToThrowTheme {
        ImagePickerField(
            imageUri = null,
            onPickImageClick = {}
        )
    }
}
