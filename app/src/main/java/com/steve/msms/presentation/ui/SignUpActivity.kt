package com.steve.msms.presentation.ui

import android.app.ListActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.Moshi
import com.steve.msms.BuildConfig
import com.steve.msms.R
import com.steve.msms.data.csv.CsvUtils
import com.steve.msms.databinding.ActivitySignUpBinding
import com.steve.msms.domain.model.Message
import com.steve.msms.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var mStorage: FirebaseStorage

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://msms-81a20-default-rtdb.firebaseio.com/")
    private var logRef: DatabaseReference = database.getReference("steve")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("The user registered to the app")

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()


        binding.btnReg.setOnClickListener {
            RegisterUser()
       }

    }

    private fun RegisterUser(){
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.registerEmail.text.toString().trim()
        val phoneNumber = binding.phoneNo.text.toString().trim()
        val password = binding.passwordReg.text.toString().trim()

//        val userInfo = User(
//            firstName,
//            lastName,
//            email,
//            getString(R.string.phone_number_prefix) + phoneNumber
//        )

//        val moshi = Moshi.Builder().build()
//        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
//        val json = jsonAdapter.toJson(userInfo)
//
//        val sharedPreferences = getSharedPreferences(BuildConfig.SHARED_PREF_NAME, MODE_PRIVATE)
//        sharedPreferences.edit {
//            putString("userInfo", json)
//            apply()
//        }


        if (email.isNotEmpty() && lastName.isNotEmpty()
            && phoneNumber.isNotEmpty() && firstName.isNotEmpty() ){

            //use courotines
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    mAuth.createUserWithEmailAndPassword(email,password).isSuccessful
                    withContext(Dispatchers.Main){

                        uploadData()

                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        startActivity(intent)

                    }

                }catch(e: Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignUpActivity,e.message, Toast.LENGTH_LONG).show()
                    }

                }
            }

        }


    }

    private fun uploadData() {

    }


}

