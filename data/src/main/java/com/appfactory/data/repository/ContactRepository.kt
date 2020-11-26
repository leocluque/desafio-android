package com.appfactory.data.repository

import com.appfactory.data.source.model.response.ContactResponse
import io.reactivex.Single

interface ContactRepository {
    fun getContacts(): Single<List<ContactResponse>>
}