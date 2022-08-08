package kz.home.walletapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kz.home.walletapp.data.User
import kz.home.walletapp.domain.usecases.LoginUseCase
import kz.home.walletapp.domain.usecases.RegisterUseCase

class AuthViewModel(
    private val register: RegisterUseCase,
    private val login: LoginUseCase
) : ViewModel() {

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            register.insertUser(user)
        }
    }

    fun loginUser(e: String, p: String) =
        login.findUser(e, p).flowOn(Dispatchers.IO)

    fun getUsers() =
        register.getAllUsers().flowOn(Dispatchers.IO)
}