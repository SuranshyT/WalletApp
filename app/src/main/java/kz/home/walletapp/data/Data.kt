package kz.home.walletapp.data

import kz.home.walletapp.R
import kz.home.walletapp.domain.model.*

object Data {
    val tutorials = listOf(
        Tutorial(1, "Easy Registration", "It is easy to register", R.drawable.img_start),
        Tutorial(2, "Manage your finances with one click", "Your finances will be under your control", R.drawable.img_start),
        Tutorial(3, "Add many bank accounts as you want", "The app allows you add many crypto and bank accounts", R.drawable.img_start)
    )

    val accountsSums = mutableListOf(
        AccountsSum("all", 0.00),
        AccountsSum("bank", 0.00),
        AccountsSum("crypto", 0.00)
    )

    val transactionsSum = mutableListOf(
        TransactionsSum("week", 0.00, 0.00),
        TransactionsSum("month", 0.00, 0.00),
        TransactionsSum("3month", 0.00, 0.00)
    )

    val bankList = mutableListOf(
        Bank("Jusan Bank", 0.0, R.drawable.jusan_logo, "bank", "Kazakhstan"),
        Bank( "Kaspi Bank", 0.0, R.drawable.kaspi_logo, "bank", "Kazakhstan"),
        Bank("Halyk Bank", 0.0, R.drawable.halyk_logo, "bank", "Kazakhstan"),
        Bank( "Forte Bank", 0.0, R.drawable.forte_logo, "bank", "Kazakhstan")
    )

    val cryptoList = mutableListOf(
        Bank("Ethereum wallet", 0.0, R.drawable.ethereum_logo, "crypto", "USA"),
        Bank("Bitcoin wallet", 0.0, R.drawable.bitcoin_logo, "crypto", "USA")
    )

    val transactions = mutableListOf(
        Transaction("01.08.2022", "Cafe", "Jusan Bank", R.drawable.jusan_logo, 5000.0, "-"),
        Transaction("01.08.2022", "Income", "Halyk Bank", R.drawable.halyk_logo, 250000.0, "+"),
        Transaction("31.07.2022", "Taxi", "Jusan Bank", R.drawable.jusan_logo, 1500.0, "-"),
        Transaction("31.07.2022", "Magnum", "Kaspi Bank", R.drawable.kaspi_logo, 10000.0, "-")
    )
}