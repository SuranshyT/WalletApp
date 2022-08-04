package kz.home.walletapp.domain.model

import androidx.annotation.DrawableRes

data class Bank(
    val name: String,
    var value: Double,
    @DrawableRes val img: Int,
    val type: String,
    val country: String
) {
    override fun toString(): String =
        """
            $name - $value KZT
        """.trimIndent()
}