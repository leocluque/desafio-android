package com.appfactory.domain.usecase

import com.appfactory.domain.model.ContactModel
import io.reactivex.Single

interface ContactUseCase {
    fun getContacts(): Single<List<ContactModel>>
}