package com.example.suraksha.utils

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

object IconManager {
    
    fun setCalculatorIcon(context: Context) {
        try {
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, "com.example.suraksha.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, "com.example.suraksha.CalculatorAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun setNormalIcon(context: Context) {
        try {
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, "com.example.suraksha.MainActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            
            context.packageManager.setComponentEnabledSetting(
                ComponentName(context, "com.example.suraksha.CalculatorAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
