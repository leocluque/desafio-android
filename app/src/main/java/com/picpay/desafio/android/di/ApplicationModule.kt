package com.picpay.desafio.android.di

import com.appfactory.domain.usecase.ContactUseCase
import com.picpay.desafio.android.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}

val moduleDomain = module {
    factory { ContactUseCase(get()) }
}