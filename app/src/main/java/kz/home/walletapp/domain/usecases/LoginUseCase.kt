package kz.home.walletapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.User

class LoginUseCase(private val repository: RepositoryImpl) {

    fun findUser(email: String, password: String): Flow<User?> {
        return repository.findUser(email, password)
    }
}