package com.picpay.desafio.android.ui.home.states

sealed class ViewEvent {
    data class ShowError(val msg: String?) : ViewEvent()
    data class ShowLoading(var isLoading: Boolean) : ViewEvent()
}