package kz.home.walletapp.di

import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.domain.usecases.AccountsUseCase
import kz.home.walletapp.domain.usecases.LoginUseCase
import kz.home.walletapp.domain.usecases.RegisterUseCase
import kz.home.walletapp.presentation.accounts.AccountsViewModel
import kz.home.walletapp.presentation.login.AuthViewModel
import kz.home.walletapp.presentation.profile.ProfileViewModel
import kz.home.walletapp.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { RepositoryImpl(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { AccountsUseCase(get()) }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { AccountsViewModel(get(), get()) }
    viewModel { SplashViewModel() }
    viewModel { ProfileViewModel(get()) }
}

val modules = listOf(mainModule, dbModule)