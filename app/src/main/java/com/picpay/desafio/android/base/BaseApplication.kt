package com.picpay.desafio.android.base

import android.app.Application
import com.appfactory.data.di.dataModule
import com.appfactory.domain.di.moduleDomain
import com.picpay.desafio.android.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(dataModule, moduleDomain, homeModule))
        }
    }
}