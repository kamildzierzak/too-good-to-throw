package com.example.toogoodtothrow.data.local

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.example.toogoodtothrow.ui.theme.DateFormats
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val Product.expiryDate: LocalDate
    get() = LocalDate.ofEpochDay(expirationDate)

val Product.isExpired: Boolean
    get() = LocalDate.now().isAfter(expiryDate)

val Product.isOneDayBeforeExpiry: Boolean
    get() = LocalDate.now().plusDays(1) == expiryDate

fun Product.formattedDate(): String =
    expiryDate.format(DateTimeFormatter.ofPattern(DateFormats.DD_MM_YYYY))

fun Product.cardColor(cs: ColorScheme): Color = when {
    isDiscarded -> cs.surfaceVariant
    isExpired -> cs.error
    isOneDayBeforeExpiry -> cs.secondary
    else -> cs.primaryContainer
}

fun Product.textColor(cs: ColorScheme): Color =
    if (isDiscarded || isExpired) cs.onError else cs.onPrimaryContainer