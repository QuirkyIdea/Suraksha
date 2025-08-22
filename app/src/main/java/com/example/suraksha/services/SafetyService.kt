package com.example.suraksha.services

import android.app.*
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Environment
import android.media.MediaRecorder
import android.os.Binder
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.suraksha.R
import com.example.suraksha.data.SurakshaRepository
import com.example.suraksha.MainActivity
import com.example.suraksha.utils.PermissionManager
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SafetyService : Service() {
    
    companion object {
        const val ACTION_SOS = "com.example.suraksha.SOS"
        const val ACTION_START_RECORDING = "com.example.suraksha.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.example.suraksha.STOP_RECORDING"
        const val ACTION_PAUSE_RECORDING = "com.example.suraksha.PAUSE_RECORDING"
        const val ACTION_RESUME_RECORDING = "com.example.suraksha.RESUME_RECORDING"
        const val CHANNEL_ID = "suraksha_safety_channel"
        const val NOTIFICATION_ID = 1001
    }
    
    private val binder = SafetyBinder()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var repository: SurakshaRepository
    private var mediaRecorder: MediaRecorder? = null
    private var recordingFile: File? = null
    private var isRecording = false
    private var isPaused = false
    
    // India-specific emergency numbers
    private val indiaEmergencyNumbers = listOf(
        "100", // Police
        "101", // Fire
        "102", // Ambulance
        "103", // Traffic Police
        "108", // Emergency Response
        "1091", // Women Helpline
        "1098", // Child Helpline
        "112"  // Emergency Response (Pan India)
    )
    
    inner class SafetyBinder : Binder() {
        fun getService(): SafetyService = this@SafetyService
    }
    
    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        repository = SurakshaRepository(
            com.example.suraksha.SurakshaApplication.database.emergencyContactDao(),
            com.example.suraksha.SurakshaApplication.database.appSettingsDao(),
            com.example.suraksha.SurakshaApplication.database.safetyRecordDao()
        )
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SOS -> handleSOS()
            ACTION_START_RECORDING -> startRecording()
            ACTION_STOP_RECORDING -> stopRecording()
            ACTION_PAUSE_RECORDING -> pauseRecording()
            ACTION_RESUME_RECORDING -> resumeRecording()
        }
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    private fun handleSOS() {
                serviceScope.launch {
            try {
                // Start foreground service
                startForeground(NOTIFICATION_ID, createNotification("SOS Activated", "Emergency response initiated"))
                
                // Get current location
                val location = getCurrentLocation()
                
                // Start recording
                startRecording()
                
                // Send SMS to emergency contacts
                sendEmergencySMS(location)
                
                // Send SMS to India emergency numbers (if permission granted)
                if (PermissionManager.hasSmsPermission(this@SafetyService)) {
                    sendToEmergencyNumbers(location)
                }
                
                // Record the event
                repository.addSafetyRecord(
                    type = "SOS",
                    latitude = location?.latitude,
                    longitude = location?.longitude
                )
                
                // Vibrate and show notification
                showEmergencyNotification(location)
                
            } catch (e: Exception) {
                Log.e("SafetyService", "Error handling SOS: ${e.message}")
            }
        }
    }
    
    private suspend fun getCurrentLocation(): Location? {
        return withContext(Dispatchers.IO) {
            try {
                if (PermissionManager.hasLocationPermission(this@SafetyService)) {
                    val locationResult = fusedLocationClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        null
                    ).await()
                    
                    locationResult
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("SafetyService", "Error getting location: ${e.message}")
                null
            }
        }
    }
    
    private suspend fun sendEmergencySMS(location: Location?) {
        val contacts = repository.getActiveContacts().first()
        if (contacts.isEmpty()) {
            Log.w("SafetyService", "No emergency contacts found")
            return
        }
        
        val locationText = if (location != null) {
            "https://maps.google.com/?q=${location.latitude},${location.longitude}"
        } else {
            "Location not available"
        }
        
        val message = buildEmergencyMessage(locationText)
        
        contacts.forEach { contact ->
            try {
                // Send SMS
                sendSMS(contact.phoneNumber, message)
                Log.d("SafetyService", "SMS sent to ${contact.name}: ${contact.phoneNumber}")
                
                // Send WhatsApp message
                sendWhatsAppMessage(contact.phoneNumber, message)
                Log.d("SafetyService", "WhatsApp message sent to ${contact.name}: ${contact.phoneNumber}")
                
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send message to ${contact.name}: ${e.message}")
            }
        }
    }
    
    private suspend fun sendToEmergencyNumbers(location: Location?) {
        val locationText = if (location != null) {
            "https://maps.google.com/?q=${location.latitude},${location.longitude}"
        } else {
            "Location not available"
        }
        
        val message = buildEmergencyMessage(locationText)
        
        // Send to first few emergency numbers (to avoid spam)
        indiaEmergencyNumbers.take(3).forEach { number ->
            try {
                sendSMS(number, message)
                Log.d("SafetyService", "Emergency SMS sent to $number")
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send emergency SMS to $number: ${e.message}")
            }
        }
    }
    
    private fun buildEmergencyMessage(locationText: String): String {
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        return """
            🚨 EMERGENCY SOS ALERT 🚨
            
            This is an automated emergency message from Suraksha Safety App.
            
            User: ${getUserName()}
            Time: $timestamp
            Location: $locationText
            
            Please respond immediately!
            
            This message was sent automatically. If this is a false alarm, please contact the user.
        """.trimIndent()
    }
    
    private fun getUserName(): String {
        // Get user name from settings or use default
        return try {
            runBlocking {
                repository.getSetting("user_name") ?: "Suraksha User"
            }
        } catch (e: Exception) {
            "Suraksha User"
        }
    }
    
    private fun sendSMS(phoneNumber: String, message: String) {
        if (!PermissionManager.hasSmsPermission(this)) {
            Log.w("SafetyService", "SMS permission not granted")
            return
        }
        
        try {
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(message)
            
            if (parts.size > 1) {
                parts.forEach { part ->
                    smsManager.sendTextMessage(phoneNumber, null, part, null, null)
                }
            } else {
                smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            }
        } catch (e: Exception) {
            Log.e("SafetyService", "Error sending SMS: ${e.message}")
            throw e
        }
    }
    
    private fun sendWhatsAppMessage(phoneNumber: String, message: String) {
        try {
            // Format phone number for WhatsApp (remove + and add country code if needed)
            val formattedNumber = phoneNumber.replace("+", "").replace(" ", "")
            val whatsappNumber = if (formattedNumber.startsWith("91")) formattedNumber else "91$formattedNumber"
            
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("https://api.whatsapp.com/send?phone=$whatsappNumber&text=${android.net.Uri.encode(message)}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            
            // Check if WhatsApp is installed
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                Log.d("SafetyService", "WhatsApp intent launched for $phoneNumber")
            } else {
                Log.w("SafetyService", "WhatsApp not installed")
            }
        } catch (e: Exception) {
            Log.e("SafetyService", "Error sending WhatsApp message: ${e.message}")
        }
    }
    
    private fun startRecording() {
        if (isRecording) {
            Log.d("SafetyService", "Recording already in progress")
            return
        }
        
        if (!PermissionManager.hasAudioPermission(this)) {
            Log.e("SafetyService", "Audio permission not granted")
            return
        }
        
        try {
            val customDirUri = try { runBlocking { repository.getSetting("recording_dir_uri") } } catch (_: Exception) { null }
            if (customDirUri.isNullOrBlank()) {
                val outputDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: filesDir
                recordingFile = File(outputDir, "emergency_recording_${System.currentTimeMillis()}.m4a")
            } else {
                // Use cache temp file path; after stop(), we will move bytes into SAF tree
                val tmp = File(cacheDir, "tmp_record_${System.currentTimeMillis()}.m4a")
                recordingFile = tmp
            }
            
            Log.d("SafetyService", "Creating recording file at: ${recordingFile?.absolutePath}")
            Log.d("SafetyService", "Output directory exists: ${outputDir.exists()}")
            Log.d("SafetyService", "Output directory writable: ${outputDir.canWrite()}")
            
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(recordingFile?.absolutePath)
                
                prepare()
                start()
            }
            
            isRecording = true
            isPaused = false
            Log.d("SafetyService", "Emergency recording started successfully: ${recordingFile?.absolutePath}")
            
            // Show notification that recording is active
            val notification = createNotification("Recording Active", "Emergency audio recording in progress")
            startForeground(NOTIFICATION_ID, notification)
            
        } catch (e: Exception) {
            Log.e("SafetyService", "Error starting recording: ${e.message}")
            isRecording = false
        }
    }
    
    private fun stopRecording() {
        if (!isRecording) {
            Log.d("SafetyService", "No recording in progress")
            return
        }
        
        try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
            isPaused = false
            
            Log.d("SafetyService", "Emergency recording stopped: ${recordingFile?.absolutePath}")
            
            // Verify file exists and log file size
            recordingFile?.let { file ->
                if (file.exists()) {
                    val fileSizeKB = file.length() / 1024
                    Log.d("SafetyService", "Recording file saved successfully. Size: ${fileSizeKB}KB")
                } else {
                    Log.e("SafetyService", "Recording file does not exist after stopping")
                }
            }
            
            // If a custom SAF directory is set, copy temp file into that folder
            val customDirUri = try { runBlocking { repository.getSetting("recording_dir_uri") } } catch (_: Exception) { null }
            if (!customDirUri.isNullOrBlank()) {
                try {
                    val treeUri = android.net.Uri.parse(customDirUri)
                    val docTree = androidx.documentfile.provider.DocumentFile.fromTreeUri(this, treeUri)
                    val fileName = "emergency_recording_${System.currentTimeMillis()}.m4a"
                    val target = docTree?.createFile("audio/mp4", fileName)
                    if (target != null && recordingFile != null && recordingFile!!.exists()) {
                        contentResolver.openOutputStream(target.uri)?.use { out ->
                            recordingFile!!.inputStream().use { input -> input.copyTo(out) }
                        }
                        // Point recordingFile to the final saved document (store uri string)
                        Log.d("SafetyService", "Recording copied to SAF folder: ${target.uri}")
                        // Update DB with content Uri path
                        serviceScope.launch {
                            repository.addSafetyRecord(
                                type = "RECORDING",
                                recordingPath = target.uri.toString()
                            )
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SafetyService", "Failed moving recording into SAF folder: ${e.message}")
                }
            }

            // Persist in safety records for local history
            serviceScope.launch {
                try {
                    repository.addSafetyRecord(
                        type = "RECORDING",
                        latitude = null,
                        longitude = null,
                        recordingPath = recordingFile?.absolutePath
                    )
                    Log.d("SafetyService", "Recording path saved to database: ${recordingFile?.absolutePath}")
                } catch (e: Exception) {
                    Log.e("SafetyService", "Failed to save recording record: ${e.message}")
                }
            }
            
            // Stop foreground service if no other emergency is active
            stopForeground(true)
            
        } catch (e: Exception) {
            Log.e("SafetyService", "Error stopping recording: ${e.message}")
            isRecording = false
        }
    }

    private fun pauseRecording() {
        if (!isRecording) {
            Log.d("SafetyService", "Cannot pause: no recording in progress")
            return
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            Log.w("SafetyService", "Pause not supported on this Android version")
            return
        }
        if (isPaused) {
            Log.d("SafetyService", "Recording already paused")
            return
        }
        try {
            mediaRecorder?.pause()
            isPaused = true
            Log.d("SafetyService", "Emergency recording paused")
            val notification = createNotification("Recording Paused", "Tap Resume in app to continue")
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            Log.e("SafetyService", "Error pausing recording: ${e.message}")
        }
    }

    private fun resumeRecording() {
        if (!isRecording) {
            Log.d("SafetyService", "Cannot resume: no recording in progress")
            return
        }
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            Log.w("SafetyService", "Resume not supported on this Android version")
            return
        }
        if (!isPaused) {
            Log.d("SafetyService", "Recording is not paused")
            return
        }
        try {
            mediaRecorder?.resume()
            isPaused = false
            Log.d("SafetyService", "Emergency recording resumed")
            val notification = createNotification("Recording Active", "Emergency audio recording in progress")
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            Log.e("SafetyService", "Error resuming recording: ${e.message}")
        }
    }
    
    private fun showEmergencyNotification(location: Location?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🚨 SOS Emergency Alert")
            .setContentText("Emergency response activated. Tap to open app.")
            .setSmallIcon(R.drawable.ic_safety)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 1000, 500, 1000, 500, 1000))
            .setLights(0xFF0000, 3000, 3000)
            .build()
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    private fun createNotification(title: String, content: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_safety)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
    }
    
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Suraksha Safety",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Emergency notifications from Suraksha Safety App"
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopRecording()
        serviceScope.cancel()
    }
}
