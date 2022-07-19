package com.steve.msms.domain.model

import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
)
