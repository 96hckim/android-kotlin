package com.hocheol.face_detective

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun Context.hasPermissions(permissionList: List<String>): Boolean {
        return permissionList.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun Activity.requestPermissions(permissionList: List<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), requestCode)
    }
}