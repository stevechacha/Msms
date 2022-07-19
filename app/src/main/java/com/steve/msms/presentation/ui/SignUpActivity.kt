package com.steve.msms.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.annotations.JsonAdapter
import com.squareup.moshi.Moshi
import com.steve.msms.BuildConfig
import com.steve.msms.R
import com.steve.msms.databinding.ActivitySignUpBinding
import com.steve.msms.domain.model.User
import timber.log.Timber

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("The user registered to the app")

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            RegisterUser()
       }

    }

    private fun RegisterUser(){
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.registerEmail.text.toString().trim()
        val phoneNumber = binding.phoneNo.text.toString().toString()

        val userInfo = User(
            firstName,
            lastName,
            email,
            getString(R.string.phone_number_prefix) + phoneNumber
        )

//        val moshi = Moshi.Builder().build()
//        val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
//        val json = jsonAdapter.toJson(userInfo)
//
//        val sharedPreferences = getSharedPreferences(BuildConfig.SHARED_PREF_NAME, MODE_PRIVATE)
//        sharedPreferences.edit {
//            putString("userInfo", json)
//            apply()
//        }


//        if (email.isNotEmpty() && lastName.isNotEmpty()
//            && phoneNumber.isNotEmpty() && firstName.isNotEmpty() ){
//
//            //use courotines
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    mAuth.createUserWithEmailAndPassword(email,password).isSuccessful
//                    withContext(Dispatchers.Main){
//                        checkLoggedInState()
//
//                    }
//
//                }catch(e: Exception){
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(this@SignUpActivity,e.message, Toast.LENGTH_LONG).show()
//                    }
//
//                }
//            }
//
//        }


    }

    private fun checkLoggedInState() {

        if (mAuth.currentUser==null){

            Toast.makeText(this@SignUpActivity,"You are Not Logged In", Toast.LENGTH_LONG).show()

        } else{
            Toast.makeText(this@SignUpActivity,"You are Logged In", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

}

