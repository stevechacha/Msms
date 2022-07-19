package com.steve.msms.data.datasource

import android.content.Context
import android.net.Uri
import android.os.FileUtils
import android.provider.Telephony
import androidx.lifecycle.MutableLiveData
import com.steve.msms.data.csv.CsvUtils
import com.steve.msms.data.remote.SmsAPI
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.SmsData
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SmsDataSource @Inject constructor(@ApplicationContext val context: Context) {
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

            val smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)).toLong()
            val date = Date()
            date.time =smsDate
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

        messageList.forEach {
           uploadData()
        }
        smsLiveData.value = SmsData(
            creditList,
            debitList,
            creditAmount,
            debitAmount
        )


        val credits = arrayListOf<Message>()
        val mFile = CsvUtils.toCsvFile(context,credits, "credit")
        Timber.i("File Exists: ${mFile.exists()}")


    }
    private fun uploadData () {


        val credits = arrayListOf<Message>()
        val mFile = CsvUtils.toCsvFile(context,credits, "credit")
        Timber.i("File Exists: ${mFile.exists()}")



        val description = RequestBody.create(
            MultipartBody.FORM,  mFile
        )

//        val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
//        val body = MultipartBody.Part.createFormData("nam", file.name, requestBody)

//        service.uploadData(mFile)





    }



}


