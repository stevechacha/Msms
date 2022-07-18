package com.steve

import android.app.Application
import android.text.format.DateFormat.format
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.lang.String.format
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@HiltAndroidApp
class MsmsApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.plant(RemoteLogsTree())
    }
}



class RemoteLogsTree: Timber.Tree(){
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://msms-81a20-default-rtdb.firebaseio.com/")
    private var logRef: DatabaseReference = database.getReference("logs")

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when(priority){
            Log.VERBOSE->{UploadLogsToFirebase("verbose",tag,message)}
            Log.DEBUG -> {UploadLogsToFirebase("debug",tag,message)}
            Log.INFO ->  {UploadLogsToFirebase("info",tag,message)}
            Log.WARN ->  {UploadLogsToFirebase("warn",tag,message)}
            Log.ERROR -> {UploadLogsToFirebase("error",tag,message)}

        }
    }

    fun UploadLogsToFirebase(level: String,tag: String?,message: String){

        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = Date()
        val strDate: String = dateFormat.format(date).toString()

        logRef.child("$strDate" + "").setValue("Level: $level ,Tag: $tag, Message: $message")


    }

}


