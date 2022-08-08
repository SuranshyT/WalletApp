package kz.home.walletapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.User

class RegisterUseCase(private val repository: RepositoryImpl) {

    suspend fun insertUser(user: User) {
        repository.insertUser(user)
    }

    fun getAllUsers(): Flow<List<User>?> {
        return repository.getAllUsers()
    }
}