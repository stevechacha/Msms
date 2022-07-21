package com.steve.msms.presentation.ui

import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.steve.msms.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_app)


        installedApps()


    }

        private fun installedApps() {
            val list = packageManager.getInstalledPackages(0)
            for (i in list.indices) {
                val packageInfo = list[i]
                if (packageInfo!!.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                    val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                    Log.e("App List$i", appName)
                }
            }
        }



}