package kz.home.walletapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import kz.home.walletapp.data.Account
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.Transaction
import kz.home.walletapp.data.User

class AccountsUseCase(private val repository: RepositoryImpl) {

    fun getAccounts(email: String): Flow<List<Account>?> {
        return repository.getAccounts(email)
    }

    suspend fun insertUser(user: User){
        return repository.insertUser(user)
    }

    fun getTransactions(email: String): Flow<List<Transaction>?> {
        return repository.getTransactions(email)
    }
}