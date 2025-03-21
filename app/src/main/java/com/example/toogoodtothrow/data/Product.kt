package com.example.toogoodtothrow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val expirationDate: Long, // long because timestamp
    val category: ProductCategory,
    val quantity: Int? = null,
    val isExpired: Boolean = false,
    val isDiscarded: Boolean = false,
    val imageUrl: String? = null
)