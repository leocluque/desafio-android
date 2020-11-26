package com.appfactory.domain.usecase

import com.appfactory.data.repository.ContactRepository
import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.model.toContactModel
import io.reactivex.Single

class ContactUseCaseImp(private val contactApi: ContactRepository) : ContactUseCase {
    override fun getContacts(): Single<List<ContactModel>> {
        return contactApi.getContacts().map { it.toContactModel() }
    }
}