package com.steve.msms.data.datasource

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.util.Log.*
import androidx.startup.StartupLogger.e
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.commons.logging.Log
import timber.log.Timber
import timber.log.Timber.Forest.e
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PInfo {
    var appname = ""
    var pname = ""
    var versionName = ""
    var versionCode = 0
    var icon: Drawable? = null
    fun prettyPrint() {
        Timber.i(appname + "\t" + pname + "\t" + versionName + "\t" + versionCode)
    }

}





