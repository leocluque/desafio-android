package com.appfactory.domain.repository

import com.appfactory.domain.model.ContactModel
import io.reactivex.Single

interface ContactRepository {
    fun getContacts(): Single<List<ContactModel>>
}