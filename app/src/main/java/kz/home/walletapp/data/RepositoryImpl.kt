package kz.home.walletapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val db: MyDatabase) {

    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }

    fun findUser(email: String, password: String): Flow<User?> {
        return flow { emit(db.userDao().findByEmail(email, password)) }
    }

    fun getUser(email: String, password: String): User? {
        return db.userDao().findByEmail(email, password)
    }

    fun getAllUsers(): Flow<List<User>?> {
        return flow { emit(db.userDao().getAllUsers()) }
    }

    suspend fun insertAccounts(user: User, accounts: List<Account>) {
        db.userDao().insertAccountList(user, accounts)
    }

    fun getAccounts(email: String): Flow<List<Account>?> {
        return flow { emit(db.userDao().getAccountList(email).accounts) }
    }

    suspend fun insertTransactions(user: User, transactions: List<Transaction>) {
        db.userDao().insertTranscationsList(user, transactions)
    }

    fun getTransactions(email: String): Flow<List<Transaction>?> {
        return flow { emit(db.userDao().getTransactionsList(email).transactions) }
    }
}