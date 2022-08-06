package kz.home.walletapp.presentation.accounts

import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kz.home.walletapp.data.Account
import kz.home.walletapp.data.Data
import kz.home.walletapp.data.Transaction
import kz.home.walletapp.data.User
import kz.home.walletapp.domain.model.*
import java.text.SimpleDateFormat
import java.util.*

class AccountsViewModel(
    private val useCase: AccountsUseCase,
    private val login: LoginUseCase
) : ViewModel() {
    private val _accounts = MutableLiveData<List<Bank>>()
    val accounts: LiveData<List<Bank>> = _accounts
    private val _sums = MutableLiveData<List<AccountsSum>>()
    val sums: LiveData<List<AccountsSum>> = _sums

    private val _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>> = _transactions
    private val _transactionsSums = MutableLiveData<List<TransactionsSum>>()
    val transactionsSums: LiveData<List<TransactionsSum>> = _transactionsSums

    private var allAccounts = mutableListOf<Bank>()
    private val allAccountsSums = mutableListOf<AccountsSum>()

    private var allTransactions = mutableListOf<Transaction>()
    private val allTransactionsSums = mutableListOf<TransactionsSum>()

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var phone: String

    private val _logOutState = MutableLiveData<Boolean>()
    val logOutState: LiveData<Boolean> = _logOutState

    fun getUser(e: String, p: String) =
        login.findUser(e, p).flowOn(Dispatchers.IO)

    fun initialize(e: String, p: String) {
        email = e
        password = p

        viewModelScope.launch {
            getUser(e, p).collect {
                if (it != null) {
                    firstName = it.firstName
                    lastName = it.lastName
                    phone = it.phone
                }
            }
        }
        viewModelScope.launch {
            getAccounts(e).collect { list ->
                val x = list?.map {
                    Bank(it.name, it.value, it.img, it.type, it.country)
                }
                if (x != null && allAccounts.isEmpty()
                ) {
                    allAccounts.addAll(x)
                    _accounts.postValue(allAccounts)

                    allAccountsSums.clear()
                    allAccountsSums.addAll(calculate())
                    _sums.postValue(allAccountsSums)
                } else if(x == null && allAccounts.isEmpty()){
                    allAccountsSums.clear()
                    allAccountsSums.addAll(Data.accountsSums)
                    _sums.postValue(allAccountsSums)
                } else {
                    allAccountsSums.clear()
                    allAccountsSums.addAll(calculate())
                    _sums.postValue(allAccountsSums)
                }
            }
        }
    }

    fun initializeTransactions(e: String, p: String){
        email = e
        password = p
        viewModelScope.launch {
            getTransactions(e).collect { list ->
                if (list != null && allTransactions.isEmpty()
                ) {
                    allTransactions.addAll(list)
                    _transactions.postValue(allTransactions)

                    allTransactionsSums.clear()
                    allTransactionsSums.addAll(calculateTransactions())
                    _transactionsSums.postValue(allTransactionsSums)
                } else if(list == null && allTransactions.isEmpty()){
                    allTransactionsSums.clear()
                    allTransactionsSums.addAll(Data.transactionsSum)
                    _transactionsSums.postValue(allTransactionsSums)
                } else {
                    allTransactionsSums.clear()
                    allTransactionsSums.addAll(calculateTransactions())
                    _transactionsSums.postValue(allTransactionsSums)
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

        allTransactions.forEach {
            val date = Calendar.getInstance()
            date.time = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(it.date) as Date

            val today = Calendar.getInstance()
            val weekAgo = Calendar.getInstance()
            weekAgo.add(Calendar.DAY_OF_MONTH, -7)

            val monthAgo = Calendar.getInstance()
            monthAgo.add(Calendar.MONTH, -1)

            val month3Ago = Calendar.getInstance()
            month3Ago.add(Calendar.MONTH, -3)

            if(date.compareTo(today) <= 0 && date.compareTo(month3Ago) >= 0 ){
                if(it.type == "+"){
                    month3SumEarned += it.value
                }else{
                    month3SumSpent += it.value
                }
                if(date.compareTo(today) <= 0 && date.compareTo(monthAgo) >= 0 ){
                    if(it.type == "+"){
                        monthSumEarned += it.value
                    }else{
                        monthSumSpent += it.value
                    }
                    if(date.compareTo(today) <= 0 && date.compareTo(weekAgo) >= 0 ){
                        if(it.type == "+"){
                            weekSumEarned += it.value
                        }else{
                            weekSumSpent += it.value
                        }
                    }
                }
            }
        }

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

    fun addAccount(bank: Bank): Boolean {
        var contains = 0
        for (i in allAccounts.indices) {
            if (allAccounts[i].name == bank.name) {
                contains = 1
                break
            }
        }

        return if (contains == 0) {
            val initValue = bank.value
            bank.value = 0.0
            allAccounts.add(bank)
            val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
            addTransaction(Transaction(currentDate, "Initial", bank.name, bank.img, initValue, "+"))

            _accounts.postValue(allAccounts)
            updateAccountsSums()

            synchronize()
            true
        }else {
            false
        }
    }

    fun addTransaction(transaction: Transaction) {
        allTransactions.add(transaction)
        _transactions.postValue(allTransactions)

        allTransactionsSums.clear()
        allTransactionsSums.addAll(calculateTransactions())
        _transactionsSums.postValue(allTransactionsSums)

        allAccounts.forEach {
            if(it.name == transaction.bank){
                if(transaction.type == "-"){
                    it.value -= transaction.value
                }else {
                    it.value += transaction.value
                }
            }
        }

        _accounts.postValue(allAccounts)
        synchronize()
    }

    fun deleteAccount(bank: Bank) {
        val transactionListToDelete = mutableListOf<Transaction>()
        allTransactions.forEach {
            if(it.bank == bank.name){
                transactionListToDelete.add(it)
            }
        }
        allAccounts.remove(bank)
        _accounts.postValue(allAccounts)
        updateAccountsSums()

        allTransactions.removeAll(transactionListToDelete)
        _transactions.postValue(allTransactions)
        updateTransactionsSums()

        synchronize()
    }

    fun deleteTransaction(transaction: Transaction) {
        allTransactions.remove(transaction)
        _transactions.postValue(allTransactions)
        updateTransactionsSums()

        allAccounts.forEach {
            if(it.name == transaction.bank){
                if(transaction.type == "-"){
                    it.value += transaction.value
                }else {
                    it.value -= transaction.value
                }
            }
        }
        _accounts.postValue(allAccounts)
        updateAccountsSums()

        synchronize()
    }

    private fun synchronize() {
        val list = allAccounts.map {
            Account(it.name, it.value, it.img, it.type, it.country)
        }

        val user = User(email, password, firstName, lastName, phone, list, allTransactions)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertUser(user)
        }
    }

    /*fun saveTransactions() {
        val list = allTransactions
        //val user = User(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            val user = useCase.getUser(email, password) ?: User(email = email, password = password, transactions = list)
            useCase.insertTransactions(user, list)
        }
    }*/

    private fun getAccounts(e: String): Flow<List<Account>?> {
        return useCase.getAccounts(e).flowOn(Dispatchers.IO)
    }

    private fun getTransactions(e: String): Flow<List<Transaction>?> {
        return useCase.getTransactions(e).flowOn(Dispatchers.IO)
    }

    fun getMyAccounts(): List<Bank> {
        return allAccounts
    }

    private fun clear() {
        allAccounts.clear()
        allAccountsSums.clear()

        allTransactions.clear()
        allTransactionsSums.clear()
    }

    private fun updateAccountsSums(){
        allAccountsSums.clear()
        allAccountsSums.addAll(calculate())
        _sums.postValue(allAccountsSums)
    }

    private fun updateTransactionsSums(){
        allTransactionsSums.clear()
        allTransactionsSums.addAll(calculateTransactions())
        _transactionsSums.postValue(allTransactionsSums)
    }

    fun logOut(){
        clear()
        _logOutState.postValue(true)
    }

    fun logIn(){
        _logOutState.postValue(false)
    }
}