package com.example.toogoodtothrow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expirationDate: Long,
    val category: ProductCategory,
    val quantity: Int? = null,
    val unit: String? = null,
    val isDiscarded: Boolean = false,
    val imagePath: String? = null,
)