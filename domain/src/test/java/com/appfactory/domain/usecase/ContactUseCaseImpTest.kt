package com.appfactory.domain.usecase

import com.appfactory.data.repository.ContactRepository
import com.appfactory.data.source.model.response.ContactResponse
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ContactUseCaseImpTest {

    @Mock
    lateinit var contactRepository: ContactRepository
    lateinit var contactUseCase: ContactUseCase

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        contactUseCase = ContactUseCaseImp(contactRepository)
    }

    @Test
    fun shouldBeConvertedToModelList() {
        whenever(contactRepository.getContacts()).thenReturn(getMockUserList())

        contactUseCase.getContacts()
            .test()
            .assertValue { it[1].name == "Leo teste2" }

        verify(contactRepository).getContacts()
    }

    @After
    fun after() {
        Mockito.verifyNoMoreInteractions(contactRepository)
    }

    private fun getMockUserList() = Single.just(
        listOf(
            ContactResponse(12, "Leo teste1", "leoTeste1", ""),
            ContactResponse(123, "Leo teste2", "leoTeste2", "")
        )
    )
}