package com.picpay.desafio.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.usecase.ContactUseCase
import com.picpay.desafio.android.extensions.singleSubscribe
import com.picpay.desafio.android.ui.home.states.HomeViewState
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val contactUseCase: ContactUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var stateHome = MutableLiveData<HomeViewState>()
    val homeViewState: LiveData<HomeViewState> = stateHome

    init {
        stateHome.value = HomeViewState().showLoading()
    }

    fun getUsers() {
        compositeDisposable.add(contactUseCase.getContacts().singleSubscribe(onSuccess = {
            showContacts(it)
        }, onError = {
            showError()
        }))
    }

    private fun showContacts(list: List<ContactModel>) =
        setState(homeViewState.value?.showContacts(list))

    private fun showError() = setState(stateHome.value?.showError())

    private fun setState(state: HomeViewState?) {
        stateHome.value = state
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}