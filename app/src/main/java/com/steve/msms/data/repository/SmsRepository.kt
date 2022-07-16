package com.steve.msms.data.repository

import androidx.lifecycle.MutableLiveData
import com.steve.msms.data.datasource.SmsDataSource
import com.steve.msms.data.local.AppDatabase
import com.steve.msms.data.local.Tag
import com.steve.msms.data.local.TaggedSmsDao
import com.steve.msms.domain.model.SmsData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsRepository @Inject constructor(
    private val smsDataSource: SmsDataSource,
    private val db: AppDatabase
) {
    init {
        smsDataSource.getSms()
    }

    fun getSmsLiveData(): MutableLiveData<SmsData> {
        smsDataSource.getSms()
        return smsDataSource.smsLiveData
    }

    suspend fun checkIfMessageIsSaved(id: String) = db.taggedSmsDao().getTagsWithId(id)?.tag
    suspend fun insertTaggedMessageId(tag: Tag) = db.taggedSmsDao().insertTag(tag)
    suspend fun getTaggedMessages(tagString: String) = db.taggedSmsDao().getTags(tagString)

    // insert transaction
    suspend fun insertTagSms(tag: Tag) = db.taggedSmsDao().insertTagSms(
        tag
    )
    // get all transaction
    fun getAllTags() = db.taggedSmsDao().getAllTags()

    // update transaction
    suspend fun updateTagSms(tag: Tag) = db.taggedSmsDao().updateTagSms(
        tag
    )


}