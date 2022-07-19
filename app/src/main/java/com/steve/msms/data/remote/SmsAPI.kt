package com.steve.msms.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface SmsAPI {

    @Multipart
    @POST("/log")
    suspend fun uploaadSms(
        @Part part: MultipartBody.Part
    ) : ResponseBody

    @Multipart
    @POST("/logs")
    suspend fun upload(
        @Part file: Part?
    ): ResponseBody
}
