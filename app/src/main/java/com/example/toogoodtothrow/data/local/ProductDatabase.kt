package com.example.toogoodtothrow.data.local

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.toogoodtothrow.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
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

                val tomatoPath = copyDrawableToFile(context, R.drawable.tomato, "tomato.jpg")
                val creamPath = copyDrawableToFile(context, R.drawable.cream, "cream.jpg")
                val eggPath = copyDrawableToFile(context, R.drawable.egg, "egg.jpg")
                val oinmentPath = copyDrawableToFile(context, R.drawable.ointment, "ointment.jpg")
                val toothpastePath =
                    copyDrawableToFile(context, R.drawable.toothpaste, "toothpaste.jpg")
                val cheesePath = copyDrawableToFile(context, R.drawable.cheese, "cheese.jpg")
                val youghurtPath = copyDrawableToFile(context, R.drawable.yogurt, "yogurt.jpg")
                val milkPath = copyDrawableToFile(context, R.drawable.milk, "milk.jpg")
                val bananaPath = copyDrawableToFile(context, R.drawable.banana, "banana.jpg")
                val herbShampooPath =
                    copyDrawableToFile(context, R.drawable.herbshampoo, "herbshampoo.jpg")
                val ibuprofenPath =
                    copyDrawableToFile(context, R.drawable.ibuprofen, "ibuprofen.jpg")
                val butterPath = copyDrawableToFile(context, R.drawable.butter, "butter.jpg")


                val listOfExampleProducts = listOf(
                    Product(
                        id = 1,
                        name = "Pomidory",
                        expirationDate = LocalDate.now().plusDays(7).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 1,
                        unit = "kg",
                        isDiscarded = false,
                        imagePath = tomatoPath
                    ),
                    Product(
                        id = 2,
                        name = "Śmietana 12%",
                        expirationDate = LocalDate.now().plusDays(1).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 1,
                        unit = "pojemnik",
                        isDiscarded = false,
                        imagePath = creamPath

                    ),
                    Product(
                        id = 3,
                        name = "Jajka",
                        expirationDate = LocalDate.now().minusDays(10).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 10,
                        unit = "szt",
                        isDiscarded = false,
                        imagePath = eggPath
                    ),
                    Product(
                        id = 4,
                        name = "Maść na ból czubka nosa",
                        expirationDate = LocalDate.now().minusMonths(1).toEpochDay(),
                        category = ProductCategory.MEDICINE,
                        quantity = 1,
                        unit = "opakowanie",
                        isDiscarded = true,
                        imagePath = oinmentPath
                    ),
                    Product(
                        id = 5,
                        name = "Pasta do zębów",
                        expirationDate = LocalDate.now().plusMonths(24).toEpochDay(),
                        category = ProductCategory.COSMETICS,
                        quantity = 1,
                        unit = "tubka",
                        isDiscarded = false,
                        imagePath = toothpastePath
                    ),
                    Product(
                        id = 6,
                        name = "Ser żółty",
                        expirationDate = LocalDate.now().plusDays(5).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 1,
                        unit = "kg",
                        isDiscarded = false,
                        imagePath = cheesePath
                    ),
                    Product(
                        id = 7,
                        name = "Jogurt naturalny",
                        expirationDate = LocalDate.now().plusDays(4).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 4,
                        unit = "opakowania",
                        isDiscarded = false,
                        imagePath = youghurtPath
                    ),
                    Product(
                        id = 8,
                        name = "Mleko 2%",
                        expirationDate = LocalDate.now().plusDays(2).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 1,
                        unit = "l",
                        isDiscarded = false,
                        imagePath = milkPath
                    ),
                    Product(
                        id = 9,
                        name = "Banany",
                        expirationDate = LocalDate.now().plusDays(3).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 6,
                        unit = "szt",
                        isDiscarded = false,
                        imagePath = bananaPath
                    ),
                    Product(
                        id = 10,
                        name = "Szampon ziołowy",
                        expirationDate = LocalDate.now().plusMonths(18).toEpochDay(),
                        category = ProductCategory.COSMETICS,
                        quantity = 1,
                        unit = "butelka",
                        isDiscarded = false,
                        imagePath = herbShampooPath
                    ),
                    Product(
                        id = 11,
                        name = "Ibuprofen 200 mg",
                        expirationDate = LocalDate.now().plusMonths(6).toEpochDay(),
                        category = ProductCategory.MEDICINE,
                        quantity = 1,
                        unit = "opakowanie",
                        isDiscarded = false,
                        imagePath = ibuprofenPath
                    ),
                    Product(
                        id = 12,
                        name = "Masło",
                        expirationDate = LocalDate.now().minusDays(3).toEpochDay(),
                        category = ProductCategory.FOOD,
                        quantity = 200,
                        unit = "g",
                        isDiscarded = false,
                        imagePath = butterPath
                    )
                )

                dao.insertAll(listOfExampleProducts)
            }
        }
    }
}

@SuppressLint("ResourceType")
private fun copyDrawableToFile(
    context: Context,
    @DrawableRes resId: Int,
    fileName: String
): String {
    val file = File(context.filesDir, fileName)
    context.resources.openRawResource(resId).use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}