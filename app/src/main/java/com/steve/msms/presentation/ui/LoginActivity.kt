package com.steve.msms.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.steve.msms.R
import com.steve.msms.databinding.ActivityLoginBinding
import com.steve.msms.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.i("The user Logged in to app")

        mAuth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
//
//    override fun onStart() {
//        super.onStart()
//        checkLoggedInState()
//    }
//
//    private fun LoginUser(){
//        val email= binding.loginEmail.text.toString().trim()
//        val password= binding.loginPassword.text.toString().trim()
//
//        if (email.isNotEmpty() && password.isNotEmpty()){
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    mAuth.signInWithEmailAndPassword(email,password).isSuccessful
//                    withContext(Dispatchers.Main){
//                        checkLoggedInState()
//                    }
//
//                }catch(e: Exception){
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(this@LoginActivity,e.message, Toast.LENGTH_LONG).show()
//                    }
//
//                }
//            }
//        }
//    }
//
//    private fun checkLoggedInState() {
//        if (mAuth.currentUser==null){
//
//            Toast.makeText(this@LoginActivity,"You are Not Logged In", Toast.LENGTH_LONG).show()
//
//        } else{
//            Toast.makeText(this@LoginActivity,"You are Logged In", Toast.LENGTH_LONG).show()
//
//        }
//
//    }
}