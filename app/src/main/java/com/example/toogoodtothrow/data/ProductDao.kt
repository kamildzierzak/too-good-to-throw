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

    // GET all products SORTED BY expiration date, closest to expiration first.
    @Query("SELECT * FROM products ORDER BY expirationDate ASC")
    fun getAllProducts(): Flow<List<Product>>

    // GET only non-expired products.
    @Query("SELECT * FROM products WHERE isExpired = 0 ORDER BY expirationDate ASC")
    fun getValidProducts(): Flow<List<Product>>

    // GET only expired products.
    @Query("SELECT * FROM products WHERE isExpired = 1 ORDER BY expirationDate ASC")
    fun getExpiredProducts(): Flow<List<Product>>

    // GET products by category.
    @Query("SELECT * FROM products WHERE category = :category ORDER BY expirationDate ASC")
    fun getProductsByCategory(category: ProductCategory): Flow<List<Product>>

    // Get specific product by ID.
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Flow<Product?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    // Mark product as discarded.
    @Query("UPDATE products SET isDiscarded = 1 WHERE id = :productId")
    suspend fun discardProduct(productId: Int)

//    @Query("DELETE FROM products WHERE isDiscarded = 1")
//    suspend fun deleteDiscardedProducts()
}