package com.appfactory.domain.usecase

import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.repository.ContactRepository
import io.reactivex.Single

class ContactUseCase(private val contactRepository: ContactRepository) {
    fun getContacts(): Single<List<ContactModel>> {
        return contactRepository.getContacts()
    }
}