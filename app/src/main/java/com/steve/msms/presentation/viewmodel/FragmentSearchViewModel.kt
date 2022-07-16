package com.steve.msms.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steve.msms.data.local.Tag
import com.steve.msms.data.repository.SmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FragmentSearchViewModel @Inject constructor(private val smsRepository: SmsRepository) :
    ViewModel() {

    private var _tagListLiveData: MutableLiveData<List<Tag>> = MutableLiveData()
    val tagListLiveData: LiveData<List<Tag>> get() = _tagListLiveData

    fun getTaggedMessages(tagString: String) {

        viewModelScope.launch {
            val messageList = smsRepository.getTaggedMessages(tagString)

            withContext(Dispatchers.Main) {
                _tagListLiveData.value = messageList
            }
        }

    }
}

