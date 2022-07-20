package kz.home.walletapp.presentation

import android.app.Application
import kz.home.walletapp.data.MyDatabase
import org.koin.core.context.GlobalContext
import kz.home.walletapp.di.modules
import org.koin.android.ext.koin.androidContext

class App : Application() {

    lateinit var db: MyDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = MyDatabase.getInstance(this)

        GlobalContext.startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}