package com.steve.msms.presentation.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.steve.msms.R
import com.steve.msms.data.csv.CsvUtils
import com.steve.msms.databinding.ActivityUploadBinding
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://msms-81a20-default-rtdb.firebaseio.com/")
    private var logRef: DatabaseReference = database.getReference("user")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.submit.setOnClickListener {
//            Registration()
//
//        }



    }


}