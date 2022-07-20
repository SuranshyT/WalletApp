package kz.home.walletapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.home.walletapp.data.User.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_USER_ID)
    val userId: Int,

    @ColumnInfo(name = COLUMN_EMAIL)
    val email: String?,

    @ColumnInfo(name = COLUMN_PASSWORD)
    val password: String? = null,

) {

    override fun toString(): String =
        """
            User(
                id=$userId,
                firstName=$email,
                lastName=$password
            )
        """.trimIndent()

    companion object {
        const val TABLE_NAME = "users"
        const val COLUMN_USER_ID = "person_id"
        const val COLUMN_EMAIL = "first_name"
        const val COLUMN_PASSWORD = "last_name"
    }
}