package com.example.toogoodtothrow.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(ProductCategoryConverter::class)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    // Singleton to prevent multiple instances of the database
    companion object {
        @Volatile
        private var Instance: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ProductDatabase::class.java,
                    "product_database"
                )
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration(true)
                    .addCallback(ProductDatabaseCallback(context))
                    .build()
                    .also { Instance = it }
            }
        }
    }

    private class ProductDatabaseCallback(private val context: Context) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = getDatabase(context).productDao()

                dao.insertAll(listOfExampleProducts)
            }
        }
    }
}

val listOfExampleProducts = listOf(
    Product(
        id = 0,
        name = "Żeberka",
        expirationDate = LocalDate.now().minusDays(2).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "kg",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 1,
        name = "Mleko 2%",
        expirationDate = LocalDate.now().plusDays(1).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
        unit = "L",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 2,
        name = "Jajka",
        expirationDate = LocalDate.now().minusDays(10).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 10,
        unit = "szt",
        isDiscarded = true,
        imageUri = null
    ),
    Product(
        id = 3,
        name = "Szampon Head & Shoulders",
        expirationDate = LocalDate.now().plusMonths(12).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "butelka",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 4,
        name = "Płyn do płukania jamy ustnej",
        expirationDate = LocalDate.now().plusMonths(24).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "L",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 5,
        name = "Ser żółty",
        expirationDate = LocalDate.now().plusDays(5).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 2,
        unit = "kg",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 6,
        name = "Jogurt naturalny",
        expirationDate = LocalDate.now().plusDays(4).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 4,
        unit = "x125g",
        isDiscarded = false,
        imageUri = null
    )
)