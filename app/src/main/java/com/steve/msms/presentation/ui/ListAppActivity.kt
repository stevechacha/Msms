package com.steve.msms.presentation.ui

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.steve.msms.R
import android.app.ActivityManager
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Log.e
import com.steve.msms.data.datasource.PInfo
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ListAppActivity : AppCompatActivity() {
    @SuppressLint("TimberArgCount")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_app)


        Timber.tag(TAG).v("Allow install apps from external?%s", isInstallingUnknownAppsAllowed())

        val activityManager =
            getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.appTasks

        for (task in tasks) {
            Timber.tag(TAG).d("stackId: %s", task.taskInfo)
        }

        val systemPackages = getDisabledPackages()

        systemPackages.forEach {
            Timber.tag(TAG).d("%s%s", "%s", it.packageName, it.enabled)

        }


        packageManager.getInstalledPackages(	PackageManager.GET_DISABLED_COMPONENTS or PackageManager.MATCH_UNINSTALLED_PACKAGES)

    }


    private fun getSystemPackages(): List<ApplicationInfo> {
        val packagesAll: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packagesAll.filter { it.flags and ApplicationInfo.FLAG_SYSTEM != 0 }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getUserPackages(): List<ApplicationInfo> {
        val packagesAll: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packagesAll.filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getDisabledPackages(): List<ApplicationInfo> {
        val packagesAll: List<ApplicationInfo> =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        return packagesAll
            .filter { !it.enabled }
        //.filter { !it.enabled }
    }


    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }


    private fun isInstallingUnknownAppsAllowed(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            packageManager.canRequestPackageInstalls()
        } else
            Settings.Secure.getInt(
                contentResolver,
                Settings.Secure.INSTALL_NON_MARKET_APPS, 0
            ) > 0
    }
    private fun getInstalledApps(getSysPackages: Boolean): ArrayList<PInfo> {
        val res = ArrayList<PInfo>()
        val packs: List<PackageInfo> = getPackageManager().getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = PInfo()
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString()
            newInfo.pname = p.packageName
            newInfo.versionName = p.versionName
            newInfo.versionCode = p.versionCode
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager())
            res.add(newInfo)
        }
        return res
    }

    private fun getPackages(): ArrayList<PInfo>? {
        val apps = getInstalledApps(false) /* false = no system packages */
        val max: Int = apps.size
        for (i in 0 until max) {
            apps[i].prettyPrint()
        }
        return apps
    }


    fun installedApps() {
        val packList: List<PackageInfo> = getPackageManager().getInstalledPackages(0)
        for (i in packList.indices) {
            val packInfo = packList[i]
            if (packInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString()
                Timber.i("App № " + Integer.toString(i), appName)
            }
        }
    }



    companion object {
        val TAG: String = ListAppActivity::class.java.simpleName
    }

}