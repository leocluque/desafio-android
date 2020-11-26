package com.picpay.desafio.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appfactory.domain.model.ContactModel
import com.appfactory.domain.usecase.ContactUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.ui.home.HomeViewModel
import com.picpay.desafio.android.ui.home.states.HomeViewState
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    private val contactUseCase: ContactUseCase = mock()
    lateinit var homeViewModel: HomeViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val expectedLoadingState = HomeViewState(
        true,
        emptyList(),
        false
    )
    private val expectedContactListState = HomeViewState(
        false,
        mockedUserResponse(),
        false
    )

    private val expectedContactEmptyListState = HomeViewState(
        false,
        emptyList(),
        false
    )

    private val expectedShowErrorState = HomeViewState(
        false,
        emptyList(),
        true
    )

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(contactUseCase)
    }

    @Test
    fun shouldSuccessWhenGetData() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(mockedUserResponse()))
        homeViewModel.getUsers()

        assertTrue(expectedContactListState == homeViewModel.homeViewState.value)
    }

    @Test
    fun shouldSuccessButEmptyList() {
        whenever(contactUseCase.getContacts()).thenReturn(Single.just(emptyList()))
        homeViewModel.getUsers()

        assertTrue(expectedContactEmptyListState == homeViewModel.homeViewState.value)
    }

    @Test
    fun shouldErrorWhenGetData() {
        val e = Throwable(message = "Ocorreu um problema tente novamente.")
        whenever(contactUseCase.getContacts()).thenReturn(Single.error(e))
        homeViewModel.getUsers()
        assertTrue(expectedShowErrorState == homeViewModel.homeViewState.value)
    }

    private fun mockedUserResponse() = listOf(
        ContactModel(12, "leoTeste1", "leoUserName1", ""),
        ContactModel(123, "leoTeste2", "leoUserName2", "")
    )
}