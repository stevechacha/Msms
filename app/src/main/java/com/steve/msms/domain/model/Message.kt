package com.steve.msms.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Message(
    val number: String,
    val body: String,
    val date: String,
    val id: String,
):Parcelable
