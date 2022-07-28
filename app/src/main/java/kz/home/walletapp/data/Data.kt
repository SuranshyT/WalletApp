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
        Sum("all", 0.00),
        Sum("bank", 0.00),
        Sum("crypto", 0.00)
    )

    val bankList = mutableListOf<Bank>(
        Bank("Jusan Bank", 0.0, R.drawable.jusan_logo, "bank"),
        Bank( "Kaspi Bank", 0.0, R.drawable.kaspi_logo, "bank"),
        Bank("Halyk Bank", 0.0, R.drawable.halyk_logo, "bank"),
        Bank( "Forte Bank", 0.0, R.drawable.forte_logo, "bank")
    )

    val cryptoList = mutableListOf<Bank>(
        Bank("Ethereum wallet", 0.0, R.drawable.ethereum_logo, "crypto"),
        Bank("Bitcoin wallet", 0.0, R.drawable.bitcoin_logo, "crypto")
    )
}