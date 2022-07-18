package com.steve.msms.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steve.msms.R
import com.steve.msms.databinding.ActivityLoginBinding
import com.steve.msms.databinding.ActivityMainBinding
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.i("The user Logged in to app")


        binding.loginBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}