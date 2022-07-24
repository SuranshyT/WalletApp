package kz.home.walletapp.domain.model

import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.User

class RegisterUseCase(private val repository: RepositoryImpl) {

    suspend fun insertUser(user: User) {
        repository.insertUser(user)
    }
}