package kz.home.walletapp.data

import androidx.room.*
import kz.home.walletapp.data.User.Companion.COLUMN_EMAIL
import kz.home.walletapp.data.User.Companion.COLUMN_PASSWORD
import kz.home.walletapp.data.User.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL = :email AND $COLUMN_PASSWORD = :password")
    fun findByEmail(email: String, password: String): User?

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllUsers(): List<User>?

    @androidx.room.Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountList(user: User, accounts: List<Account>) {
        user.accounts = accounts
        insertUser(user)
    }

    @Query("SELECT * FROM $TABLE_NAME WHERE email =:email")
    fun getAccountList(email: String): User

    @androidx.room.Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranscationsList(user: User, transactions: List<Transaction>) {
        user.transcations = transactions
        insertUser(user)
    }

    @Query("SELECT * FROM $TABLE_NAME WHERE email =:email")
    fun getTransactionsList(email: String): User
}