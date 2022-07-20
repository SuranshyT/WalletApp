package kz.home.walletapp

import android.app.Application
import org.koin.core.context.GlobalContext
import kz.home.walletapp.di.modules

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            modules(modules)
        }
    }
}