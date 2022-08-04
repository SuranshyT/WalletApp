package kz.home.walletapp.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val date: String,
    val name: String,
    val bank: String,
    @DrawableRes val img: Int,
    var value: Double,
    val type: String
) : Parcelable {
    override fun toString(): String =
        """
            Transaction(
                date=$date,
                name=$name,
                type=$type,
                value=$value   
            )
        """.trimIndent()
}