package com.steve.msms.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steve.msms.data.repository.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class FragmentPieChartViewModel  @Inject constructor(private val smsRepository: SmsRepository) : ViewModel() {


    val sms = smsRepository.getSmsLiveData()

    fun getformatedAmount(amount: Double) = formatCurrency(amount)

    private fun formatCurrency(number: Double): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(number)

        return "$formattedNumber.00"
    }
}