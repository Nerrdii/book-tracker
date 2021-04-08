package com.example.booktracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@Database(entities = [Book::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book-tracker"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.bookDao())
                }
            }
        }

        suspend fun populateDatabase(bookDao: BookDao) {
            bookDao.insertAll(INITIAL_DATA)
        }
    }
}

val INITIAL_DATA = listOf(
    Book( "Carrie", "Stephen King", LocalDate.parse("1974-04-05"), ReadingList.WANT_TO_READ, "http://books.google.com/books/content?id=FNxGvn1SCVMC&printsec=frontcover&img=1&zoom=1&edge=curl&imgtk=AFLRE73mkMwRNur8LGGB5WNMximQt5ZIw-qwG_67xmexIVdxeE95Gd-3NEl4si_NqeXaF_0HN8wR65b9Nb2SJnw7CWMv7SwpDXBLCcfAeiPNYjewWfTu_Vkqob8KO2a_n1xevGE1qlEM&source=gbs_api"),
    Book( "To Kill a Mockingbird", "Harper Lee", LocalDate.parse("1960-07-11"), ReadingList.READ, "http://books.google.com/books/content?id=PGR2AwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"),
    Book( "Lord of Chaos", "Robert Jordan", LocalDate.parse("1994-10-15"), ReadingList.READING, "http://books.google.com/books/content?id=owkKhVCq6f0C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
)
