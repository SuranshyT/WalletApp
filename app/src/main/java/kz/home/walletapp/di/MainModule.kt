package kz.home.walletapp.di

import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.domain.model.AccountsUseCase
import kz.home.walletapp.domain.model.LoginUseCase
import kz.home.walletapp.domain.model.RegisterUseCase
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.AuthViewModel
import kz.home.walletapp.presentation.splash.SplashViewModel
import kz.home.walletapp.presentation.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { RepositoryImpl(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { AccountsUseCase(get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { AccountsViewModel(get()) }
    viewModel { SplashViewModel() }
    viewModel { TransactionsViewModel() }
}

val modules = listOf(mainModule, dbModule)