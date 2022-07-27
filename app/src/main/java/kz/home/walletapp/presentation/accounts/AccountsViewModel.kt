package kz.home.walletapp.presentation.accounts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.home.walletapp.data.Data
import kz.home.walletapp.domain.model.Bank
import org.koin.core.KoinApplication.Companion.init

class AccountsViewModel : ViewModel() {
    private val _accounts = MutableLiveData<List<Bank>>()
    val accounts: LiveData<List<Bank>> = _accounts

    private val allAccounts = mutableListOf<Bank>()

    init {
        allAccounts.addAll(Data.bankList)
        _accounts.postValue(allAccounts)
    }

    fun addAccount(bank: Bank){
        allAccounts.add(bank)
        _accounts.postValue(allAccounts)
    }


}