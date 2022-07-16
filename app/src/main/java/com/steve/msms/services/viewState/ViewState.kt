package com.steve.msms.services.viewState

import com.steve.msms.data.local.Tag


sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    data class Success(val tag: List<Tag>) : ViewState()
    data class Error(val exception: Throwable) : ViewState()
}
