package com.appfactory.data

import com.appfactory.data.repository.ContactRepositoryImp
import com.appfactory.data.source.model.response.ContactResponse
import com.appfactory.data.source.remote.services.PicPayService
import com.appfactory.domain.repository.ContactRepository
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

class RepositoryImpTest {
    @Mock
    lateinit var service: PicPayService
    lateinit var repository: ContactRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        repository = ContactRepositoryImp(service)
    }

    @After
    fun after() {
        Mockito.verifyNoMoreInteractions(service)
    }

    @Test
    fun shouldBeStorageAndReturnSuccessRemoteCall() {
        whenever(service.getUsers()).thenReturn(getContactList())

        repository.getContacts().test()
            .assertValue { it[1].name == "test user 2" }

        verify(service).getUsers()
    }

    @Test
    fun shouldNotStorageAndReturnSuccessRemoteCall() {
        whenever(service.getUsers()).thenReturn(getContactList())

        repository.getContacts()
            .test()
            .assertValue { it[0].name == "test user" }

        verify(service).getUsers()
    }

    @Test
    fun shouldBeError() {
        whenever(service.getUsers()).thenReturn(Single.error(Exception("No IOException")))

        repository.getContacts()
            .test()
            .assertError { it.message == "No IOException" }

        verify(service).getUsers()
    }

    private fun getContactList() = Single.just(
        listOf(
            ContactResponse(12, "test user", "testUser", ""),
            ContactResponse(123, "test user 2", "testUser", "")
        )
    )
}