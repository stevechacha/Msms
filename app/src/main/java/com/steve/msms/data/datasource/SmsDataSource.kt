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
      //  Log.d("$messageList","Messages")
    }

    private fun checkIfMessageIsTransactional(message: Message) {
        println(message)
        var amount: String? = ""
        val rgx =
            Pattern.compile("[rR][sS]\\.?\\s[,\\d]+\\.?\\d{0,2}|[iI][nN][rR]\\.?\\s*[,\\d]+\\.?\\d{0,2}| [kK][sS][hH]\\.?\\s*[,\\d]+\\.?\\d{0,2}")


        val regexDebitOrCredit =
            Pattern.compile("(?i)(?:\\smade on|ur|made a\\s|in\\*)([A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?)")
        val regexDetectAnyTranscation =
            Pattern.compile("(?=.*[Aa]ccount.*|.*[Aa]/[Cc].*|.*[Aa][Cc][Cc][Tt].*|.*[Cc][Aa][Rr][Dd].*)(?=.*[Cc]redit.*|.*[Dd]ebit.*)(?=.*[Ii][Nn][Rr].*|.*[Rr][Ss].*)")
        val regexAmountBank =
            Pattern.compile("[rR][sS]\\.?\\s[,\\d]+\\.?\\d{0,2}|[iI][nN][rR]\\.?\\s*[,\\d]+\\.?\\d{0,2}")
        val regexMerchantname =
            Pattern.compile("(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)")


        // val matcherDetectTransaction =regexDetectAnyTranscation.matcher(message.body.plus(message.number))
        val regexAccNo =
            Pattern.compile("[0-9]*[Xx\\*]*[0-9]*[Xx\\*]+[0-9]{3,}")
        val regexBanks =
            Pattern.compile("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})\$")

        val matcherMechantName = regexMerchantname.matcher(message.body.plus(message.number))
        val matcherDebitOrCredit =regexDebitOrCredit.matcher(message.body.plus(message.number))
        val matcherDetectTransaction =regexDetectAnyTranscation.matcher(message.body)
        val matcherAmountBank =regexAmountBank.matcher(message.body.plus(message.number))
        val matcherAccountNo = regexAccNo.matcher(message.body.plus(message.number))
        val matcherBank =regexBanks.matcher(message.body.plus(message.number))

        val institution_id = listOf(
            "absabank","bangebet","barclays","betika","dtb","ecobank","equitybank","familybank",
            "gtbank","gulfbank","stanbic","coopbank","uba","africaloan","apexcaltd","cashnow","credithela",
            "grolatech","hikash","migo","okash","pesazone","tala","umba","zashloan","branchint1","kcb","mpesa",
            "ninecredit","accessbank","bayes","barry_ke","bloomkash","chapchap","counterone","directcash",
            "fadhili","finley","flashpesa","fuliza","glea","inukaafrica","jijenge","kcbmpesa","kashbora",
            "kashplus","kopacash","1_pesainfo","letshego","lioncash","loanpesa","mshwari","minikash","mokash",
            "mozzartbet","musoni","nbssicap","opesa","odibets","paddy_micro","platinum_cr","quickteller","sapesa",
            "senti","shara","skywavemgt","springpesa","timiza","usawa","utunzi","zenka","ipesa","Okoa Jahazi"
            )


       // println(message)


        if (matcherMechantName.find()
            || matcherDebitOrCredit.find()|| matcherAmountBank.find()
            || matcherAccountNo.find()||matcherBank.find() && matcherDetectTransaction.find()
        ) {

            try {
                amount = matcherDetectTransaction.group(0)?.replace("inr".toRegex(), "")
                amount = amount?.replace("rs".toRegex(), "")
                amount = amount?.replace("inr".toRegex(), "")
                amount = amount?.replace(" ".toRegex(), "")
                amount = amount?.replace(",".toRegex(), "")

            } catch (e: Exception) {

            }

        }
        try {
            amount = amount?.removePrefix("Rs")
            amount = amount?.removePrefix("Ksh")
            amount = amount?.removePrefix("KSh")
            amount = amount?.removePrefix("KSH")
        } catch (e: Exception) {

        }
        println(message.body)
        if (message.number.length<9 || message.body.contains("Credit") ) {

            try {
                if (!amount.isNullOrEmpty()) {
                    creditAmount += amount.toDouble()
                    creditList.add(message)
                  //  println(message)
                }
            } catch (e: Exception) {

            }
        }
        if (message.number.length<9) {

            try {
                if (!amount.isNullOrEmpty()) {
                    debitAmount += amount.toDouble()
                    debitList.add(message)
                }
            } catch (e: Exception) {

            }
        }


    }
}

