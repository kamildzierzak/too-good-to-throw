package com.example.toogoodtothrow.ui.common

import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import java.time.LocalDate

val previewProducts = listOf(
    Product(
        id = 1,
        name = "Pomidory",
        expirationDate = LocalDate.now().plusDays(7).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "kg",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 2,
        name = "Śmietana 12%",
        expirationDate = LocalDate.now().plusDays(1).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = null,
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 3,
        name = "Jajka",
        expirationDate = LocalDate.now().minusDays(10).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 10,
        unit = "szt",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 4,
        name = "Maść na ból czubka nosa",
        expirationDate = LocalDate.now().minusMonths(1).toEpochDay(),
        category = ProductCategory.MEDICINE,
        quantity = 1,
        unit = "opakowanie",
        isDiscarded = true,
        imageUri = null
    ),
    Product(
        id = 5,
        name = "Pasta do zębów",
        expirationDate = LocalDate.now().plusMonths(24).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "tubka",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 6,
        name = "Ser żółty",
        expirationDate = LocalDate.now().plusDays(5).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "kg",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 7,
        name = "Jogurt naturalny",
        expirationDate = LocalDate.now().plusDays(4).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 4,
        unit = "opakowania",
        isDiscarded = false,
        imageUri = null
    )
)