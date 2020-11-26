package com.appfactory.data.source.remote.services

import com.appfactory.data.source.model.response.ContactResponse
import io.reactivex.Single
import retrofit2.http.GET

interface PicPayService {

    @GET("users")
    fun getUsers(): Single<List<ContactResponse>>

}