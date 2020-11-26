package com.appfactory.data.di

import com.appfactory.data.repository.ContactRepositoryImp
import com.appfactory.data.source.remote.NetworkConstants.BASE_URL
import com.appfactory.data.source.remote.ServiceGenerator
import com.appfactory.data.source.remote.services.PicPayService
import com.appfactory.domain.repository.ContactRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        ServiceGenerator.createService(
            serviceClass = PicPayService::class.java,
            url = BASE_URL,
            context = androidContext()
        )
    }
    factory<ContactRepository> { ContactRepositoryImp(get()) }
}
