package kz.home.walletapp.domain.model

import androidx.annotation.DrawableRes

data class Transaction(
    val date: String,
    val name: String,
    val bank: String,
    @DrawableRes val img: Int,
    var value: Double,
    val type: String
)