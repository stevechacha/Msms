package com.steve.msms.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steve.msms.data.local.Tag
import com.steve.msms.data.repository.SmsRepository
import com.steve.msms.domain.model.Message
import com.steve.msms.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddTagViewModel @Inject constructor(private val smsRepository: SmsRepository) :
    ViewModel() {

    private val _tagAdded = MutableLiveData<Event<String>>()
    val tagAdded: LiveData<Event<String>>
        get() = _tagAdded
    var tag: String? = null
    var message: Message? = null

    fun saveTags() {

        if (tag.isNullOrEmpty()) return

        if (tag?.trim()!!.isEmpty()) return

        viewModelScope.launch(Dispatchers.Default) {
            val newRowId = smsRepository.insertTaggedMessageId(
                Tag(
                    tag!!,
                    message!!.id, message!!
                )
            )
            withContext(Dispatchers.Main) {
                if (newRowId > -1) {
                    _tagAdded.value =
                        Event("Tag Added Successfully")
                } else {
                    _tagAdded.value =
                        Event("Error Occurred")
                }
            }
        }

    }


}