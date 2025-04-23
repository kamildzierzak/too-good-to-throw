package com.example.toogoodtothrow.ui.common

import com.example.toogoodtothrow.data.local.Product
import com.example.toogoodtothrow.data.local.ProductCategory
import java.time.LocalDate

val previewProducts = listOf(
    Product(
        id = 1,
        name = "Żeberka",
        expirationDate = LocalDate.now().minusDays(2).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "kg",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 2,
        name = "Mleko 2%",
        expirationDate = LocalDate.now().plusDays(1).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
        unit = "L",
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
        isDiscarded = true,
        imageUri = null
    ),
    Product(
        id = 4,
        name = "Szampon Head & Shoulders",
        expirationDate = LocalDate.now().plusMonths(12).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "butelka",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 5,
        name = "Płyn do płukania jamy ustnej",
        expirationDate = LocalDate.now().plusMonths(24).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "L",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 6,
        name = "Ser żółty",
        expirationDate = LocalDate.now().plusDays(5).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
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
        unit = "x125g",
        isDiscarded = false,
        imageUri = null
    )
)