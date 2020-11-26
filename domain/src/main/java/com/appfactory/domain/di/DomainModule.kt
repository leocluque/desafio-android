package com.appfactory.domain.di

import com.appfactory.domain.usecase.ContactUseCase
import org.koin.dsl.module

val moduleDomain = module {
    factory<ContactUseCase> { ContactUseCase(get()) }
}