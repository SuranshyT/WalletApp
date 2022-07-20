package kz.home.walletapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val userDao: UserDao) {

     fun insertUser(user: User) {
         userDao.insertUser(user)
    }
}