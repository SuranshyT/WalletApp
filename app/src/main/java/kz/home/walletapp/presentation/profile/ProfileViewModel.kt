package kz.home.walletapp.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kz.home.walletapp.domain.model.LoginUseCase

class ProfileViewModel(
    private val login: LoginUseCase
) : ViewModel() {

    fun loginUser(e: String, p: String) =
        login.findUser(e, p).flowOn(Dispatchers.IO)
}