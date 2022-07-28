package kz.home.walletapp.presentation.accounts

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kz.home.walletapp.data.Account
import kz.home.walletapp.data.Data
import kz.home.walletapp.data.User
import kz.home.walletapp.domain.model.AccountsUseCase
import kz.home.walletapp.domain.model.Bank
import kz.home.walletapp.domain.model.Sum

class AccountsViewModel(
    private val useCase: AccountsUseCase
) : ViewModel() {
    private val _accounts = MutableLiveData<List<Bank>>()
    val accounts: LiveData<List<Bank>> = _accounts
    private val _sums = MutableLiveData<List<Sum>>()
    val sums: LiveData<List<Sum>> = _sums

    private var allAccounts = mutableListOf<Bank>()
    private val allSums = mutableListOf<Sum>()

    private lateinit var email: String
    private lateinit var password: String
    var n = 0

    fun initialize(e: String, p: String) {
        email = e
        password = p
        //allAccounts.clear()
        //_accounts.postValue(allAccounts)
        Log.e("", "1: ${allAccounts.isEmpty()}")
        viewModelScope.launch {
            getAccounts(e).collect { list ->
                val x = list?.map {
                    Bank(it.name, it.value, it.img, it.type)
                }
                if (x != null && allAccounts.isEmpty()
                ) {
                    allAccounts.addAll(x)
                    _accounts.postValue(allAccounts)
                    Log.e("", "2: ${allAccounts.isEmpty()}")

                    allSums.addAll(calculate())
                    _sums.postValue(allSums)
                } else {
                    allSums.addAll(Data.sums)
                    _sums.postValue(allSums)
                }
            }
        }
    }

    private fun calculate(): List<Sum> {
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
            Sum("all", sum1),
            Sum("bank", sum2),
            Sum("crypto", sum3)
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

        allSums.addAll(calculate())
        _sums.postValue(allSums)

        saveAccounts()
    }

    fun deleteAccount(bank: Bank) {
        allAccounts.remove(bank)
        _accounts.postValue(allAccounts)

        allSums.addAll(calculate())
        _sums.postValue(allSums)

        saveAccounts()
    }

    fun saveAccounts() {
        val list = allAccounts.map {
            Account(it.name, it.value, it.img, it.type)
        }
        val user = User(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            useCase.insertAccounts(user, list)
        }
    }

    private fun getAccounts(e: String): Flow<List<Account>?> {
        return useCase.getAccounts(e).flowOn(Dispatchers.IO)
    }

    fun getUser(e: String, p: String) {
        email = e
        password = p
    }

    fun count() {
        n += 1
    }

    fun getCount() = n

    fun clear() {
        allAccounts.clear()
        //_accounts.postValue(allAccounts)
    }
}