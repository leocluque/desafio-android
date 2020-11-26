package com.picpay.desafio.android.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appfactory.domain.usecase.ContactUseCase
import com.picpay.desafio.android.extensions.singleSubscribe
import com.picpay.desafio.android.ui.home.states.ViewEvent
import com.picpay.desafio.android.ui.home.states.ViewState
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val contactApi: ContactUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var eventHome = MutableLiveData<ViewEvent>()
    val viewEvent = eventHome
    private var stateHome = MutableLiveData<ViewState>()
    val viewState = stateHome

    fun getUsers() {
        eventHome.value = ViewEvent.ShowLoading(true)
        compositeDisposable.add(contactApi.getContacts().singleSubscribe(onSuccess = {
            eventHome.value = ViewEvent.ShowLoading(false)
            stateHome.value = ViewState.ShowContacts(it)
        }, onError = {
            eventHome.value = ViewEvent.ShowLoading(false)
            stateHome.value = ViewState.ShowContacts(emptyList())
            eventHome.value = ViewEvent.ShowError(it.message)
        }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}