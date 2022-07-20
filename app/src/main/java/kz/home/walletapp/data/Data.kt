package kz.home.walletapp.data

import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Tutorial

object Data {
    val tutorials = listOf<Tutorial>(
        Tutorial(1, "Easy Registration", "It is easy to register", R.drawable.img_start),
        Tutorial(2, "Manage your finances with one click", "Your finances will be under your control", R.drawable.img_start),
        Tutorial(3, "Add many bank accounts as you want", "The app allows you add many crypto and bank accounts", R.drawable.img_start)
    )
}