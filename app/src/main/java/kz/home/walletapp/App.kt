package kz.home.walletapp

import android.app.Application
import kz.home.walletapp.data.MyDatabase
import org.koin.core.context.GlobalContext
import kz.home.walletapp.di.modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}