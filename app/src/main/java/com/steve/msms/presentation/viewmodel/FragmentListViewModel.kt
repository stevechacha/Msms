package com.steve.msms.presentation.viewmodel


import androidx.lifecycle.ViewModel
import com.steve.msms.data.repository.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FragmentListViewModel  @Inject constructor(private val smsRepository: SmsRepository) : ViewModel() {


    fun checkIfMessagedHasTag(id: String): String? = runBlocking {
        smsRepository.checkIfMessageIsSaved(id)
    }

}