package kz.home.walletapp.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class SplashViewModel : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn = _isLoggedIn

    init {
        _isLoggedIn.postValue(false)
    }

    fun setLoggedIn(){
        _isLoggedIn.postValue(true)
    }
}