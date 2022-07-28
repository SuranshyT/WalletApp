package kz.home.walletapp.data

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.parcelize.Parcelize
import kz.home.walletapp.data.Account.Companion.TABLE_NAME

@Entity(
    tableName = TABLE_NAME)
/*    ,
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("email"),
        childColumns = arrayOf("userEmail"),
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)*/
@Parcelize
data class Account(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_VALUE)
    var value: Double,

    @ColumnInfo(name = COLUMN_IMAGE)
    val img: Int,

    @ColumnInfo(name = COLUMN_TYPE)
    val type: String
) : Parcelable {
    override fun toString(): String =
        """
            User(
                name=$name,
                value=$value,
                type=$type                
            )
        """.trimIndent()

    companion object {
        const val TABLE_NAME = "accounts"
        const val COLUMN_NAME = "name"
        const val COLUMN_VALUE = "value"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_TYPE = "type"
    }
}