package com.example.toogoodtothrow.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    // Flow to observe changes in the data and automatically update the UI.

    @Query("SELECT * FROM product ORDER BY expirationDate ASC")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getProductById(id: Int): Flow<Product>

    @Query("SELECT * FROM product WHERE category = :category ORDER BY expirationDate ASC")
    fun getProductsByCategory(category: ProductCategory): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE isExpired = :isExpired ORDER BY expirationDate ASC")
    fun getProductsByExpiredStatus(isExpired: Boolean): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}