package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.usecase.ContactUseCase
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.ui.home.HomeViewModel
import com.picpay.desafio.android.ui.home.states.ViewEvent
import com.picpay.desafio.android.ui.home.states.ViewState
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {
    @Mock
    lateinit var contactUseCase: ContactUseCase

    @InjectMocks
    lateinit var homeViewModel: HomeViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldSuccessWhenGetData() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(mockedUserResponse()))

        homeViewModel.getUsers()

        assertTrue(ViewState.ShowContacts(mockedUserResponse()) == homeViewModel.viewState.value)
    }

    @Test
    fun shouldSuccessButEmptyList() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(emptyList()))

        homeViewModel.getUsers()

        assertTrue(ViewState.ShowContacts(emptyList()) == homeViewModel.viewState.value)
    }

    @Test
    fun shouldErrorWhenGetData() {
        val e = Throwable(message = "Ocorreu um problema tente novamente.")
        whenever(contactUseCase.getContacts()).thenReturn(Single.error(e))

        homeViewModel.getUsers()

        assertTrue(homeViewModel.viewEvent.value == ViewEvent.ShowError(e.message))
    }

    private fun mockedUserResponse() = listOf(
        ContactModel(12, "leoTeste1", "leoUserName1", ""),
        ContactModel(123, "leoTeste2", "leoUserName2", "")
    )
}