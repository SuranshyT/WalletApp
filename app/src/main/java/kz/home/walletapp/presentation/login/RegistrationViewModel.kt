package kz.home.walletapp.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.home.walletapp.data.MyDatabase
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.User
import kz.home.walletapp.presentation.App
import kz.home.walletapp.utils.Executors
import kz.home.walletapp.utils.randomID

class RegistrationViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    val executors = Executors()

    //var list = MutableLiveData<MutableList<Currency>>()

    fun insertData(e: String, p: String) {
        //viewModelScope.launch {
        executors.diskIO().execute {
            val insert = User(randomID(), e, p)

            repository.insertUser(insert)
        }
    }
}