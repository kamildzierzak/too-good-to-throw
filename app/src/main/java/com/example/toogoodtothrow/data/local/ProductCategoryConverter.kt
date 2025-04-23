package com.example.toogoodtothrow.data.local

import androidx.room.TypeConverter

class ProductCategoryConverter {
    @TypeConverter
    fun fromProductCategory(category: ProductCategory): String {
        return category.name
    }

    @TypeConverter
    fun toProductCategory(categoryName: String): ProductCategory {
        return ProductCategory.valueOf(categoryName)
    }
}