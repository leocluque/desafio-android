package com.picpay.desafio.android.ui.home.states

import com.appfactory.domain.model.ContactModel

sealed class ViewState {
    data class ShowContacts(val contacts: List<ContactModel>) : ViewState()
}