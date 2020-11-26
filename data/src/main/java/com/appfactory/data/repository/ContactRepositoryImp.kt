package com.appfactory.data.repository

import com.appfactory.data.source.model.response.ContactMapper
import com.appfactory.data.source.remote.services.PicPayService
import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.repository.ContactRepository
import io.reactivex.Single

class ContactRepositoryImp(private val contactApi: PicPayService) : ContactRepository {

    override fun getContacts(): Single<List<ContactModel>> {
        return contactApi.getUsers().map { ContactMapper().toContactModel(it) }
    }
}