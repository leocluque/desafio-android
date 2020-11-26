package com.appfactory.domain.di

import com.appfactory.domain.usecase.ContactUseCase
import com.appfactory.domain.usecase.ContactUseCaseImp
import org.koin.dsl.module

val moduleDomain = module {
    factory<ContactUseCase> { ContactUseCaseImp(get()) }
}