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

private val listOfExampleProducts = listOf(
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
    ),
    Product(
        id = 8,
        name = "Mleko 2%",
        expirationDate = LocalDate.now().plusDays(2).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 1,
        unit = "l",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 9,
        name = "Banany",
        expirationDate = LocalDate.now().plusDays(3).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 6,
        unit = "szt",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 10,
        name = "Szampon ziołowy",
        expirationDate = LocalDate.now().plusMonths(18).toEpochDay(),
        category = ProductCategory.COSMETICS,
        quantity = 1,
        unit = "butelka",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 11,
        name = "Ibuprofen 200 mg",
        expirationDate = LocalDate.now().plusMonths(6).toEpochDay(),
        category = ProductCategory.MEDICINE,
        quantity = 1,
        unit = "opakowanie",
        isDiscarded = false,
        imageUri = null
    ),
    Product(
        id = 12,
        name = "Masło",
        expirationDate = LocalDate.now().minusDays(3).toEpochDay(),
        category = ProductCategory.FOOD,
        quantity = 200,
        unit = "g",
        isDiscarded = false,
        imageUri = null
    )
)
