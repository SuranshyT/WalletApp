package kz.home.walletapp.domain.model

import kotlinx.coroutines.flow.Flow
import kz.home.walletapp.data.Account
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.User

class AccountsUseCase(private val repository: RepositoryImpl) {

    suspend fun insertAccounts(user: User, accounts: List<Account>) {
        repository.insertAccounts(user, accounts)
    }

    fun getAccounts(email: String): Flow<List<Account>?> {
        return repository.getAccounts(email)
    }
}