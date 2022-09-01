package com.hamiltoncapital.hamiltonpay

import android.app.Application
import com.hamiltoncapital.hamiltonpay.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HamiltonPayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            androidContext(this@HamiltonPayApplication)
            modules(appModule)
        }
    }
}