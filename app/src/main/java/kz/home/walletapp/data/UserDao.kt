package kz.home.walletapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kz.home.walletapp.data.User.Companion.COLUMN_EMAIL
import kz.home.walletapp.data.User.Companion.COLUMN_PASSWORD
import kz.home.walletapp.data.User.Companion.COLUMN_USER_ID
import kz.home.walletapp.data.User.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Query("""SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL LIKE :email 
            AND $COLUMN_PASSWORD LIKE :password LIMIT 1""")
    fun findByEmail(email: String, password: String): User
}