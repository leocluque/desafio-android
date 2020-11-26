package com.appfactory.data.repository

import com.appfactory.data.source.model.response.ContactResponse
import com.appfactory.data.source.remote.services.PicPayService
import io.reactivex.Single

class ContactRepositoryImp(private val contactApi: PicPayService) : ContactRepository {

    override fun getContacts(): Single<List<ContactResponse>> {
        return contactApi.getUsers()
    }
}