package com.steve.msms.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.steve.msms.R
import com.steve.msms.databinding.ActivityMainBinding
import com.steve.msms.utils.Event
import com.steve.msms.utils.EventObject
import com.steve.msms.utils.SMS_PERMISSION_REQUEST
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            SMS_PERMISSION_REQUEST -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    EventObject.statusMessage.value =
                        Event("Permission Granted")
                }
            } else {
                EventObject.statusMessage.value =
                    Event("Permission Denied")
            }
        }
    }
}