package com.appfactory.data.source.model.response

import com.appfactory.domain.model.ContactModel

class ContactMapper {

    fun toContactModel(listMap: List<ContactResponse>): List<ContactModel> {
        val list = arrayListOf<ContactModel>()
        listMap.forEach {
            list.add(ContactModel(id = it.id, name = it.name, username = it.username, img = it.img))
        }
        return list
    }
}