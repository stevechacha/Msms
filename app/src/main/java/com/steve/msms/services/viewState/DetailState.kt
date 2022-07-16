package com.steve.msms.services.viewState

import com.steve.msms.data.local.Tag


sealed class DetailState {
    object Loading : DetailState()
    object Empty : DetailState()
    data class Success(val tag: Tag) : DetailState()
    data class Error(val exception: Throwable) : DetailState()
}
