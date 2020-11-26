package com.picpay.desafio.android.ui.home.states

import com.appfactory.domain.model.ContactModel

data class HomeViewState(
    val isLoading: Boolean = true,
    val contactList: List<ContactModel> = emptyList(),
    val showError: Boolean = false
) {
    fun showContacts(contacts: List<ContactModel>) = this.copy(
        isLoading = false,
        contactList = contacts,
        showError = false
    )

    fun showError() = this.copy(
        isLoading = false,
        showError = true,
        contactList = emptyList()
    )

    fun showLoading() = this.copy(
        isLoading = true,
        showError = false,
        contactList = emptyList()
    )
}

