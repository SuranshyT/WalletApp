package kz.home.walletapp.di

import android.content.Context
import androidx.room.Room
import kz.home.walletapp.data.MyDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(androidApplication(), provideDatabaseName()) }
}

fun provideDatabaseName(): String = "MyDB"

fun provideDatabase(context: Context, databaseName: String): MyDatabase {
    return Room.databaseBuilder(context, MyDatabase::class.java, databaseName).build()
}