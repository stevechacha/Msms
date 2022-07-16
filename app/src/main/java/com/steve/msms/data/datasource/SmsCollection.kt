package com.steve.msms.data.datasource



import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.SmsData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsCollection @Inject constructor(@ApplicationContext val context: Context) {
    private val creditList = arrayListOf<Message>()
    private val debitList = arrayListOf<Message>()
    private var creditAmount = 0.0
    private var debitAmount = 0.0
    val smsLiveData = MutableLiveData<SmsData>()

    fun getSms() {
        val messageList = arrayListOf<Message>()

        val cursor =
            context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)

        while (cursor != null && cursor.moveToNext()) {

            val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
            val number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))
            val id = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID))


            messageList.add(
                Message(
                    number,
                    body,
                    Date(smsDate.toLong()),
                    id
                )
            )
        }

        cursor?.close()

        messageList.forEach { message ->
            checkIfMessageIsTransactional(message)
        }
        smsLiveData.value = SmsData(
            creditList,
            debitList,
            creditAmount,
            debitAmount
        )
//        Log.d("$messageList","Messages")
    }

    private fun checkIfMessageIsTransactional(message: Message) {
      //  print(message)

    }
}
