package com.steve.msms.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SmsData(
    val creditSmsList: List<Message>,
    val DebitSmsList: List<Message>,
    val TotalCreditedAmount: Double,
    val totalDebitedAmount: Double
) : Parcelable