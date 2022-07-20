package kz.home.walletapp.di

import android.app.Application
import androidx.room.Room
import kz.home.walletapp.data.MyDatabase
import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.data.UserDao
import kz.home.walletapp.presentation.login.RegistrationViewModel
import kz.home.walletapp.utils.Executors
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    //single { MyDatabase.getInstance(androidContext()) }

    fun provideDatabase(application: Application): MyDatabase {
    return Room.databaseBuilder(application, MyDatabase::class.java, "my-database")
        .build()
}

    fun provideCountriesDao(database: MyDatabase): UserDao {
        return  database.userDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }

    /*single {
        Room.databaseBuilder(
            get(),
            MyDatabase::class.java, "my-database"
        ).build()
    }*/

    /*single<UserDao> {
        val database = get<MyDatabase>()
        database.userDao()
    }*/

    factory { RepositoryImpl(get()) }

    viewModel { RegistrationViewModel(get()) }
}

val modules = listOf(mainModule)