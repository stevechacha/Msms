package com.steve.msms.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steve.msms.data.local.Tag
import com.steve.msms.data.repository.SmsRepository
import com.steve.msms.services.exportcsv.ExportCsvService
import com.steve.msms.services.exportcsv.toCsv
import com.steve.msms.services.viewState.DetailState
import com.steve.msms.services.viewState.ExportState
import com.steve.msms.services.viewState.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val smsRepository: SmsRepository,
    private val exportService: ExportCsvService,
) : ViewModel() {

    // state for export csv status
    private val _exportCsvState = MutableStateFlow<ExportState>(ExportState.Empty)
    val exportCsvState: StateFlow<ExportState> = _exportCsvState

    private val _transactionFilter = MutableStateFlow("Overall")
    val transactionFilter: StateFlow<String> = _transactionFilter

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Loading)
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)

    // UI collect from this stateFlow to get the state updates
    val uiState: StateFlow<ViewState> = _uiState
    val detailState: StateFlow<DetailState> = _detailState


    // export all Transactions to csv file
    fun exportTagToCsv(csvFileUri: Uri) = viewModelScope.launch {
        _exportCsvState.value = ExportState.Loading
        smsRepository
            .getAllTags()
            .flowOn(Dispatchers.IO)
            .map { it.toCsv() }
            .flatMapMerge { exportService.writeToCSV(csvFileUri, it) }
            .catch { error ->
                _exportCsvState.value = ExportState.Error(error)
            }.collect { uriString ->
                _exportCsvState.value = ExportState.Success(uriString)
            }
    }
    // insert transaction
    fun insertTags(tag: Tag) = viewModelScope.launch {
        smsRepository.insertTagSms(tag)
    }

    // update transaction
    fun updateTags(tag: Tag) = viewModelScope.launch {
        smsRepository.updateTagSms(tag)
    }


}