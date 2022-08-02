package kz.home.walletapp.presentation.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.home.walletapp.domain.model.Transaction
import kz.home.walletapp.domain.model.TransactionsSum
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
        var weekSum1 = 0.0
        var weekSum2 = 0.0
        var monthSum1 = 0.0
        var monthSum2 = 0.0
        var month3Sum1 = 0.0
        var month3Sum2 = 0.0

        /*val string = "January 2, 2010";
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)
        LocalDate date = LocalDate.parse(string, formatter)

        DateFormat.parse(stringDate)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())



        //val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())*/
        /*val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_YEAR, -7)
        for (i in allTransactions.indices) {
            weekSum1 += allTransactions[i].value
            weekSum2 += allTransactions[i].value
            if (allTransactions[i].type == "bank") {
                monthSum1 += allTransactions[i].value
                monthSum2 += allTransactions[i].value
            } else if (allTransactions[i].type == "crypto"){
                month3Sum1 += allTransactions[i].value
                month3Sum2 += allTransactions[i].value
            }
        }*/
        return listOf(
            TransactionsSum("week", weekSum1, weekSum2),
            TransactionsSum("month", monthSum1, monthSum2),
            TransactionsSum("3month", month3Sum1, month3Sum2)
        )
    }

    fun addAccount(transaction: Transaction) {
        allTransactions.add(transaction)
        _transactions.postValue(allTransactions)

        /*allAccountsSums.addAll(calculate())
        _sums.postValue(allAccountsSums)*/
    }

    fun deleteTransaction(transaction: Transaction) {
        allTransactions.remove(transaction)
        _transactions.postValue(allTransactions)

        /*allAccountsSums.addAll(calculate())
        _sums.postValue(allAccountsSums)*/
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