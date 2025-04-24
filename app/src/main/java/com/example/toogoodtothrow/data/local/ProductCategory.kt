package com.example.toogoodtothrow.data.local

enum class ProductCategory {
    FOOD, MEDICINE, COSMETICS
}

fun ProductCategory.toPolish(): String {
    return when (this) {
        ProductCategory.FOOD -> "Jedzenie"
        ProductCategory.MEDICINE -> "Leki"
        ProductCategory.COSMETICS -> "Kosmetyki"
    }
}