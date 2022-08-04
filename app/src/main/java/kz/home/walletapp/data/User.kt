package kz.home.walletapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.home.walletapp.data.User.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_EMAIL)
    val email: String,

    @ColumnInfo(name = COLUMN_PASSWORD)
    val password: String,

    @ColumnInfo(name = COLUMN_ACCOUNTS)
    var accounts: List<Account>? = null,

    @ColumnInfo(name = COLUMN_TRANSACTIONS)
    var transcations: List<Transaction>? = null
) {
    override fun toString(): String =
        """
            User(
                email=$email,
                password=$password
            )
        """.trimIndent()

    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_ACCOUNTS = "accounts"
        const val COLUMN_TRANSACTIONS = "transactions"
    }
}