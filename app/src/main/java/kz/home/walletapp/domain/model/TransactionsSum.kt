package kz.home.walletapp.domain.model

data class TransactionsSum(
    val type: String,
    var earned: Double,
    var spent: Double
)