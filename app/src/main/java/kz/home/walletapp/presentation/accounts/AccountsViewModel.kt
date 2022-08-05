package kz.home.walletapp.presentation.accounts

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kz.home.walletapp.data.Account
import kz.home.walletapp.data.Data
import kz.home.walletapp.data.Transaction
import kz.home.walletapp.data.User
import kz.home.walletapp.domain.model.AccountsUseCase
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.domain.model.AccountsSum
import kz.home.walletapp.domain.model.TransactionsSum

class AccountsViewModel(
    private val useCase: AccountsUseCase
) : ViewModel() {
    private val _accounts = MutableLiveData<List<Bank>>()
    val accounts: LiveData<List<Bank>> = _accounts
    private val _sums = MutableLiveData<List<AccountsSum>>()
    val sums: LiveData<List<AccountsSum>> = _sums

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions
    private val _transactionSums = MutableLiveData<List<TransactionsSum>>()
    val transactionsSums: LiveData<List<TransactionsSum>> = _transactionSums

    private var allAccounts = mutableListOf<Bank>()
    private val allAccountsSums = mutableListOf<AccountsSum>()

    private var allTransactions = mutableListOf<Transaction>()
    private val allTransactionsSums = mutableListOf<TransactionsSum>()

    private lateinit var email: String
    private lateinit var password: String
    var n = 0

    private val _logOutState = MutableLiveData<Boolean>()
    val logOutState: LiveData<Boolean> = _logOutState

    fun initialize(e: String, p: String) {
        email = e
        password = p
        //allAccounts.clear()
        //_accounts.postValue(allAccounts)
        Log.e("", "1: ${allAccounts.isEmpty()}")
        viewModelScope.launch {
            getAccounts(e).collect { list ->
                val x = list?.map {
                    Bank(it.name, it.value, it.img, it.type, it.country)
                }
                if (x != null && allAccounts.isEmpty()
                ) {
                    allAccounts.addAll(x)
                    _accounts.postValue(allAccounts)
                    Log.e("", "2: ${allAccounts.isEmpty()}")

                    allAccountsSums.clear()
                    allAccountsSums.addAll(calculate())
                    _sums.postValue(allAccountsSums)
                } else if(x == null && allAccounts.isEmpty()){
                    allAccountsSums.clear()
                    allAccountsSums.addAll(Data.accountsSums)
                    _sums.postValue(allAccountsSums)
                } else {
                    allAccountsSums.clear()
                    //allAccountsSums.addAll(Data.accountsSums)
                    allAccountsSums.addAll(calculate())
                    _sums.postValue(allAccountsSums)
                }
            }
        }
    }

    fun initializeTransactions(e: String, p: String){
        email = e
        password = p
        //allAccounts.clear()
        //_accounts.postValue(allAccounts)
        viewModelScope.launch {
            getTransactions(e).collect { list ->
                val x = list
                /*list?.map {
                    Bank(it.name, it.value, it.img, it.type)
                }*/
                if (x != null && allTransactions.isEmpty()
                ) {
                    allTransactions.addAll(x)
                    _transactions.postValue(allTransactions)

                    allTransactionsSums.clear()
                    allTransactionsSums.addAll(calculateTransactions())
                    _transactionSums.postValue(allTransactionsSums)
                } else if(x == null && allTransactions.isEmpty()){
                    allTransactionsSums.clear()
                    allTransactionsSums.addAll(Data.transactionsSum)
                    _transactionSums.postValue(allTransactionsSums)
                } else {
                    allTransactionsSums.clear()
                    //allAccountsSums.addAll(Data.accountsSums)
                    allTransactionsSums.addAll(calculateTransactions())
                    _transactionSums.postValue(allTransactionsSums)
                }
            }
        }
    }

    private fun calculate(): List<AccountsSum> {
        var sum1 = 0.0
        var sum2 = 0.0
        var sum3 = 0.0
        for (i in allAccounts.indices) {
            sum1 += allAccounts[i].value
            if (allAccounts[i].type == "bank") {
                sum2 += allAccounts[i].value
            } else if (allAccounts[i].type == "crypto"){
                sum3 += allAccounts[i].value
            }
        }
        return listOf(
            AccountsSum("all", sum1),
            AccountsSum("bank", sum2),
            AccountsSum("crypto", sum3)
        )
    }

    private fun calculateTransactions(): List<TransactionsSum> {
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

    fun addAccount(bank: Bank) {
        var contains = 0
        for (i in allAccounts.indices) {
            if (allAccounts[i].name == bank.name) {
                allAccounts[i].value = bank.value
                contains = 1
                break
            }
        }

        if (contains == 0) {
            allAccounts.add(bank)
        }
        _accounts.postValue(allAccounts)
        allAccountsSums.clear()
        allAccountsSums.addAll(calculate())
        _sums.postValue(allAccountsSums)

        saveAccounts()
    }

    fun addTransaction(transaction: Transaction) {
        /*allTransactions.add(transaction)
        _transactions.postValue(allTransactions)*/

        var contains = 0
        for (i in allTransactions.indices) {
            if (allTransactions[i].name == transaction.name) {
                allTransactions[i].value = transaction.value
                contains = 1
                break
            }
        }

        if (contains == 0) {
            allTransactions.add(transaction)
        }
        _transactions.postValue(allTransactions)
        allTransactionsSums.clear()
        allTransactionsSums.addAll(calculateTransactions())
        _transactionSums.postValue(allTransactionsSums)

        saveTransactions()
    }

    fun deleteAccount(bank: Bank) {
        allAccounts.remove(bank)
        _accounts.postValue(allAccounts)
        allAccountsSums.clear()
        allAccountsSums.addAll(calculate())
        _sums.postValue(allAccountsSums)

        saveAccounts()
    }

    fun deleteTransaction(transaction: Transaction) {
        /*allTransactions.remove(transaction)
        _transactions.postValue(allTransactions)*/

        allTransactions.remove(transaction)
        _transactions.postValue(allTransactions)
        allTransactionsSums.clear()
        allTransactionsSums.addAll(calculateTransactions())
        _transactionSums.postValue(allTransactionsSums)

        saveTransactions()
    }

    private fun saveAccounts() {
        val list = allAccounts.map {
            Account(it.name, it.value, it.img, it.type, it.country)
        }
        val user = User(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertAccounts(user, list)
        }
    }

    fun saveTransactions() {
        val list = allTransactions
        /*.map {
            Account(it.name, it.value, it.img, it.type)
        }*/
        val user = User(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertTransactions(user, list)
        }
    }

    private fun getAccounts(e: String): Flow<List<Account>?> {
        return useCase.getAccounts(e).flowOn(Dispatchers.IO)
    }

    private fun getTransactions(e: String): Flow<List<Transaction>?> {
        return useCase.getTransactions(e).flowOn(Dispatchers.IO)
    }

    fun getUser(e: String, p: String) {
        email = e
        password = p
    }

    fun getMyAccounts(): List<Bank> {
        return allAccounts
    }

    fun count() {
        n += 1
    }

    fun getCount() = n

    fun clear() {
        allAccounts.clear()
        allAccountsSums.clear()

        allTransactions.clear()
        allTransactionsSums.clear()
        //_accounts.postValue(allAccounts)
    }

    fun logOut(){
        clear()
        _logOutState.postValue(true)
    }

    fun logIn(){
        _logOutState.postValue(false)
    }
}