package kz.home.walletapp.data

import androidx.room.*
import kz.home.walletapp.data.User.Companion.COLUMN_EMAIL
import kz.home.walletapp.data.User.Companion.COLUMN_PASSWORD
import kz.home.walletapp.data.User.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = :email AND $COLUMN_PASSWORD = :password")
    fun findByEmail(email: String, password: String): User?
}