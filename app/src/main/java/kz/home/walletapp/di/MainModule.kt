package kz.home.walletapp.di

import kz.home.walletapp.data.RepositoryImpl
import kz.home.walletapp.domain.model.LoginUseCase
import kz.home.walletapp.domain.model.RegisterUseCase
import kz.home.walletapp.presentation.login.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { RepositoryImpl(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    viewModel { AuthViewModel(get(), get()) }
}

val modules = listOf(mainModule, dbModule)