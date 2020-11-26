package com.appfactory.data.source.model.response

import com.appfactory.domain.model.ContactModel
import org.junit.Assert.assertTrue
import org.junit.Test

class ContactMapperTest {

    private val expectedContactList = listOf(
        ContactModel(123, "teste", "teste", "")
    )
    private val contactResponseList = listOf(
        ContactResponse(123, "teste", "teste", "")
    )

    @Test
    fun `map contactResponse Should convert into contactModel`() {
        assertTrue(ContactMapper().toContactModel(contactResponseList) == expectedContactList)
    }
}
