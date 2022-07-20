package kz.home.walletapp.domain.model

import androidx.annotation.DrawableRes

data class Tutorial(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)
