package com.appfactory.domain.model

import com.appfactory.data.source.model.response.ContactResponse

data class ContactModel(
    val id: Int,
    val name: String,
    val username: String,
    val img: String
)

fun List<ContactResponse>.toContactModel(): List<ContactModel> {
    val list = arrayListOf<ContactModel>()
    this.forEach {
        list.add(ContactModel(id = it.id, name = it.name, username = it.username, img = it.img))
    }
    return list
}