package kz.home.walletapp.domain.model

import androidx.annotation.DrawableRes

data class Bank(
    val id: Int,
    val name: String,
    val value: Double,
    @DrawableRes val img: Int
)