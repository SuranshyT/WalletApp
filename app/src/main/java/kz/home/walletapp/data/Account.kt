package kz.home.walletapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Account(
    val name: String,
    var value: Double,
    val img: Int,
    val type: String,
    val country: String
) : Parcelable {
    override fun toString(): String =
        """
            Account(
                name=$name,
                value=$value,
                type=$type                
            )
        """.trimIndent()
}