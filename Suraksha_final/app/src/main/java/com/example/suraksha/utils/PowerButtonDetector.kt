package com.example.suraksha.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.SystemClock
import android.view.KeyEvent

class PowerButtonDetector(private val context: Context) {
    
    companion object {
        private const val TAP_COUNT_THRESHOLD = 4
        private const val TAP_TIME_WINDOW = 3000L
    }
    
    private var tapCount = 0
    private var lastTapTime = 0L
    private var onPowerButtonDetected: (() -> Unit)? = null
    private var receiver: BroadcastReceiver? = null
    
    fun setOnPowerButtonListener(listener: () -> Unit) {
        onPowerButtonDetected = listener
    }
    
    fun startListening() {
        if (receiver == null) {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == Intent.ACTION_SCREEN_OFF) {
                        handlePowerButtonTap()
                    }
                }
            }
            
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_OFF)
            }
            context.registerReceiver(receiver, filter)
        }
    }
    
    fun stopListening() {
        receiver?.let {
            try {
                context.unregisterReceiver(it)
            } catch (e: Exception) {
            }
            receiver = null
        }
    }
    
    private fun handlePowerButtonTap() {
        val currentTime = SystemClock.elapsedRealtime()
        
        if (currentTime - lastTapTime > TAP_TIME_WINDOW) {
            tapCount = 1
        } else {
            tapCount++
        }
        
        lastTapTime = currentTime
        
        if (tapCount >= TAP_COUNT_THRESHOLD) {
            onPowerButtonDetected?.invoke()
            tapCount = 0
        }
    }
    
    fun reset() {
        tapCount = 0
        lastTapTime = 0L
    }
}
