package kz.home.walletapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MyDatabase =
            Room.databaseBuilder(
                context,
                MyDatabase::class.java, "my-database"
            )
                // .addMigrations(MigrationFromVersion1ToVersion2())
                .fallbackToDestructiveMigration()
                .build()
    }
}