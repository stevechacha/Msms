package com.steve.msms.data.remote

import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface SmsAPI {
    @POST("/user")
    suspend fun createUser(
        @Body user: User
    )

    @POST("/message")
    suspend fun postMessage(
        @Body message: Message

    )

}


