package com.example.toogoodtothrow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expirationDate: Long,
    val category: ProductCategory,
    val quantity: String? = null,
    val isExpired: Boolean = false,
    val isDiscarded: Boolean = false,
    val imageUrl: String? = null
)