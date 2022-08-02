package kz.home.walletapp.presentation.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.home.walletapp.domain.model.Transaction
import kz.home.walletapp.domain.model.TransactionsSum

class TransactionsViewModel() : ViewModel() {
    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions
    private val _sums = MutableLiveData<List<TransactionsSum>>()
    val sums: LiveData<List<TransactionsSum>> = _sums

    private var allTransactions = mutableListOf<Transaction>()
    private val allTransactionsSums = mutableListOf<TransactionsSum>()

    private lateinit var email: String
    private lateinit var password: String

    private fun calculate(): List<TransactionsSum> {
        var weekSumEarned = 0.0
        var weekSumSpent = 0.0
        var monthSumEarned = 0.0
        var monthSumSpent = 0.0
        var month3SumEarned = 0.0
        var month3SumSpent = 0.0

        //val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())*/
        /*val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_YEAR, -7)
        for (i in allTransactions.indices) {
            //условие неделя
                //условие type +
                    weekSumEarned += allTransactions[i].value
                //условие type -
                    weekSumSpent += allTransactions[i].value
            if (allTransactions[i].type == "bank") {
                monthSum1 += allTransactions[i].value
                monthSum2 += allTransactions[i].value
            } else if (allTransactions[i].type == "crypto"){
                month3Sum1 += allTransactions[i].value
                month3Sum2 += allTransactions[i].value
            }
        }*/
        return listOf(
            TransactionsSum("week", weekSumEarned, weekSumSpent),
            TransactionsSum("month", monthSumEarned, monthSumSpent),
            TransactionsSum("3month", month3SumEarned, month3SumSpent)
        )
    }

    fun addAccount(transaction: Transaction) {
        allTransactions.add(transaction)
        _transactions.postValue(allTransactions)

        /*allTransactionsSums.clear()
        allTransactionsSums.addAll(calculate())
        _sums.postValue(allTransactionsSums)*/
    }

    fun deleteTransaction(transaction: Transaction) {
        allTransactions.remove(transaction)
        _transactions.postValue(allTransactions)

        /*allTransactionsSums.clear()
        allTransactionsSums.addAll(calculate())
        _sums.postValue(allTransactionsSums)*/
    }

    fun getUser(e: String, p: String) {
        email = e
        password = p
    }

    fun clear() {
        allTransactions.clear()
        //_accounts.postValue(allAccounts)
    }
}