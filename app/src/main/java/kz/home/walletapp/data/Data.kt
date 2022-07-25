package kz.home.walletapp.data

import kz.home.walletapp.R
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.domain.model.Sum
import kz.home.walletapp.domain.model.Tutorial

object Data {
    val tutorials = listOf<Tutorial>(
        Tutorial(1, "Easy Registration", "It is easy to register", R.drawable.img_start),
        Tutorial(2, "Manage your finances with one click", "Your finances will be under your control", R.drawable.img_start),
        Tutorial(3, "Add many bank accounts as you want", "The app allows you add many crypto and bank accounts", R.drawable.img_start)
    )

    val sums = mutableListOf<Sum>(
        Sum(1, 0.00),
        Sum(2, 0.00),
        Sum(3, 0.00)
    )

    val bankList = mutableListOf<Bank>(
        Bank(1, "Jusan Bank", 100000.0, R.drawable.jusan_logo),
        Bank(2, "Kaspi Bank", 50000.0, R.drawable.kaspi_logo)
    )
}