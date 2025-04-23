package com.example.toogoodtothrow.ui.screens.product_edit.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.toogoodtothrow.R
import com.example.toogoodtothrow.ui.theme.Spacing
import com.example.toogoodtothrow.ui.theme.TooGoodToThrowTheme

@Composable
fun ImagePickerField(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    onImageSelected: (Uri?) -> Unit
) {
    val selectImageText = stringResource(R.string.select_image)
    val noImageText = stringResource(R.string.no_image_selected)
    val selectedImageDesc = stringResource(R.string.selected_image)
    val clearImageDesc = stringResource(R.string.clear_image)

    val launcher = rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
        onImageSelected(uri)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.padding(bottom = Spacing.Small)
        ) {
            Text(text = selectImageText)
        }

        Spacer(Modifier.height(Spacing.Small))

        if (imageUri != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .semantics { contentDescription = selectedImageDesc }
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = clearImageDesc,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(Spacing.Small)
                        .clickable { onImageSelected(null) }
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .semantics { contentDescription = noImageText },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = noImageText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePickerFieldPreview() {
    TooGoodToThrowTheme {
        Column(modifier = Modifier.padding(Spacing.Medium)) {
            ImagePickerField(imageUri = null, onImageSelected = {})
            Spacer(Modifier.height(Spacing.Medium))
            ImagePickerField(imageUri = "".toUri(), onImageSelected = {})
        }
    }
}
