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
import androidx.core.content.FileProvider
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
        const val ACTION_SEND_TEST_SMS = "com.example.suraksha.SEND_TEST_SMS"
        const val ACTION_SEND_EMERGENCY_SMS = "com.example.suraksha.SEND_EMERGENCY_SMS"
        const val ACTION_SEND_LOCATION_SMS = "com.example.suraksha.SEND_LOCATION_SMS"
        const val CHANNEL_ID = "suraksha_safety_channel"
        const val NOTIFICATION_ID = 1001
        const val LOCATION_UPDATE_INTERVAL = 10000L
    }
    
    private val binder = SafetyBinder()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var repository: SurakshaRepository
    private var mediaRecorder: MediaRecorder? = null
    private var recordingFile: File? = null
    private var isRecording = false
    private var isPaused = false
    private var currentLocation: Location? = null
    private var isLocationTracking = false
    
    private val indiaEmergencyNumbers = listOf(
        "100",
        "101",
        "102",
        "103",
        "108",
        "1091",
        "1098",
        "112"
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
        setupLocationTracking()
        createNotificationChannel()
    }
    
    private fun setupLocationTracking() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(15000)
            .build()
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    currentLocation = location
                    Log.d("SafetyService", "Location updated: ${location.latitude}, ${location.longitude}")
                    
                    if (isRecording || isLocationTracking) {
                        updateLocationNotification(location)
                    }
                }
            }
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_SOS -> handleSOS()
            ACTION_START_RECORDING -> startRecording()
            ACTION_STOP_RECORDING -> stopRecording()
            ACTION_PAUSE_RECORDING -> pauseRecording()
            ACTION_RESUME_RECORDING -> resumeRecording()
            ACTION_SEND_TEST_SMS -> handleTestSMS(intent)
            ACTION_SEND_EMERGENCY_SMS -> handleEmergencySMS(intent)
            ACTION_SEND_LOCATION_SMS -> handleLocationSMS(intent)
        }
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    private fun handleSOS() {
        serviceScope.launch {
            try {
                startForeground(NOTIFICATION_ID, createNotification("SOS Activated", "Emergency response initiated"))
                
                startLocationTracking()
                
                val location = getCurrentLocation()
                
                startRecording()
                
                sendEmergencySMS(location)
                
                sendLocationSMSToAllContacts(location)
                
                if (PermissionManager.hasSmsPermission(this@SafetyService)) {
                    sendToEmergencyNumbers(location)
                }
                
                repository.addSafetyRecord(
                    type = "SOS",
                    latitude = location?.latitude,
                    longitude = location?.longitude
                )
                
                showEmergencyNotification(location)
                
            } catch (e: Exception) {
                Log.e("SafetyService", "Error handling SOS: ${e.message}")
            }
        }
    }

    private fun handleTestSMS(intent: Intent) {
        val phoneNumber = intent.getStringExtra("phone_number") ?: return
        val contactName = intent.getStringExtra("contact_name") ?: "Contact"
        
        serviceScope.launch {
            val location = getCurrentLocation()
            val locationText = if (location != null) {
                val lat = location.latitude
                val lng = location.longitude
                val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${lat},${lng}"
                val geoUri = "geo:${lat},${lng}?q=${lat},${lng}"
                "Lat: ${lat}, Lng: ${lng}\nGoogle Maps: ${mapsUrl}\nGeo: ${geoUri}"
            } else {
                "Location not available"
            }
            
            val testMessage = """
                🧪 TEST SMS FROM SURAKSHA 🧪
                
                Hello $contactName,
                
                This is a test message from the Suraksha Safety App.
                Your emergency contact setup is working correctly.
                
                Time: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}
                Current Location: $locationText
                
                If you received this, the SMS system is working properly.
            """.trimIndent()
            
            try {
                sendSMS(phoneNumber, testMessage)
                Log.d("SafetyService", "Test SMS sent to $contactName: $phoneNumber")
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send test SMS to $contactName: ${e.message}")
            }
        }
    }

    private fun handleEmergencySMS(intent: Intent) {
        val phoneNumber = intent.getStringExtra("phone_number") ?: return
        val contactName = intent.getStringExtra("contact_name") ?: "Contact"
        
        serviceScope.launch {
            if (!isLocationTracking) {
                startLocationTracking()
            }
            val location = getCurrentLocation()
            val locationText = if (location != null) {
                val lat = location.latitude
                val lng = location.longitude
                val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${lat},${lng}"
                val geoUri = "geo:${lat},${lng}?q=${lat},${lng}"
                "Lat: ${lat}, Lng: ${lng}\nGoogle Maps: ${mapsUrl}\nGeo: ${geoUri}"
            } else {
                "Location not available"
            }
            
            val emergencyMessage = """
                🚨 EMERGENCY SOS ALERT 🚨
                
                I AM IN DANGER! Please help me immediately!
                
                User: ${getUserName()}
                Time: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}
                Location: $locationText
                
                This is an automated emergency message from Suraksha Safety App.
                Please respond or call me back immediately.
            """.trimIndent()
            
            try {
                sendSMS(phoneNumber, emergencyMessage)
                Log.d("SafetyService", "Emergency SMS sent to $contactName: $phoneNumber")
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send emergency SMS to $contactName: ${e.message}")
            }
        }
    }
    
    private fun handleLocationSMS(intent: Intent) {
        val phoneNumber = intent.getStringExtra("phone_number") ?: return
        val contactName = intent.getStringExtra("contact_name") ?: "Contact"
        
        serviceScope.launch {
            if (!isLocationTracking) {
                startLocationTracking()
            }
            
            val location = getCurrentLocation()
            val locationText = if (location != null) {
                "https://maps.google.com/?q=${location.latitude},${location.longitude}"
            } else {
                "Location not available"
            }
            
            val locationMessage = """
                📍 LOCATION SHARE FROM SURAKSHA 📍
                
                Hello $contactName,
                
                I'm sharing my current location with you.
                
                User: ${getUserName()}
                Time: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}
                Live Location: $locationText
                
                This location was shared via Suraksha Safety App.
                You can tap the link to open Google Maps.
            """.trimIndent()
            
            try {
                sendSMS(phoneNumber, locationMessage)
                Log.d("SafetyService", "Location SMS sent to $contactName: $phoneNumber")
                
                showLocationSharedNotification(contactName, location)
                
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send location SMS to $contactName: ${e.message}")
            }
        }
    }
    
    private fun startLocationTracking() {
        if (isLocationTracking) return
        
        if (!PermissionManager.hasLocationPermission(this)) {
            Log.w("SafetyService", "Location permission not granted")
            return
        }
        
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
            isLocationTracking = true
            Log.d("SafetyService", "Location tracking started")
        } catch (e: Exception) {
            Log.e("SafetyService", "Error starting location tracking: ${e.message}")
        }
    }
    
    private fun stopLocationTracking() {
        if (!isLocationTracking) return
        
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            isLocationTracking = false
            Log.d("SafetyService", "Location tracking stopped")
        } catch (e: Exception) {
            Log.e("SafetyService", "Error stopping location tracking: ${e.message}")
        }
    }
    
    private suspend fun getCurrentLocation(): Location? {
        return withContext(Dispatchers.IO) {
            try {
                if (PermissionManager.hasLocationPermission(this@SafetyService)) {
                    val lastLocation = fusedLocationClient.lastLocation.await()
                    if (lastLocation != null) {
                        currentLocation = lastLocation
                        return@withContext lastLocation
                    }
                    
                    val locationResult = fusedLocationClient.getCurrentLocation(
                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                        null
                    ).await()
                    
                    locationResult?.let { currentLocation = it }
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
            val lat = location.latitude
            val lng = location.longitude
            val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${lat},${lng}"
            val geoUri = "geo:${lat},${lng}?q=${lat},${lng}"
            "Lat: ${lat}, Lng: ${lng}\nGoogle Maps: ${mapsUrl}\nGeo: ${geoUri}"
        } else {
            "Location not available"
        }
        
        val message = buildEmergencyMessage(locationText)
        
        for (contact in contacts) {
            try {
                sendSMS(contact.phoneNumber, message)
                Log.d("SafetyService", "SMS sent to ${contact.name}: ${contact.phoneNumber}")

                kotlinx.coroutines.delay(500)
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send SMS to ${contact.name}: ${e.message}")
            }
        }
    }
    
    private suspend fun sendToEmergencyNumbers(location: Location?) {
        val locationText = if (location != null) {
            val lat = location.latitude
            val lng = location.longitude
            val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${lat},${lng}"
            val geoUri = "geo:${lat},${lng}?q=${lat},${lng}"
            "Lat: ${lat}, Lng: ${lng}\nGoogle Maps: ${mapsUrl}\nGeo: ${geoUri}"
        } else {
            "Location not available"
        }
        
        val message = buildEmergencyMessage(locationText)
        
        indiaEmergencyNumbers.take(3).forEach { number ->
            try {
                sendSMS(number, message)
                Log.d("SafetyService", "Emergency SMS sent to $number")
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send emergency SMS to $number: ${e.message}")
            }
        }
    }
    
    private suspend fun sendLocationSMSToAllContacts(location: Location?) {
        val contacts = repository.getActiveContacts().first()
        if (contacts.isEmpty()) {
            Log.w("SafetyService", "No emergency contacts found for location SMS")
            return
        }
        
        val locationText = if (location != null) {
            val lat = location.latitude
            val lng = location.longitude
            val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${lat},${lng}"
            val geoUri = "geo:${lat},${lng}?q=${lat},${lng}"
            "Lat: ${lat}, Lng: ${lng}\nGoogle Maps: ${mapsUrl}\nGeo: ${geoUri}"
        } else {
            "Location not available"
        }
        
        val locationMessage = """
            📍 LOCATION SHARE FROM SURAKSHA 📍
            
            Hello,
            
            I'm sharing my current location with you.
            
            User: ${getUserName()}
            Time: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())}
            Live Location: $locationText
            
            This location was shared via Suraksha Safety App.
            You can tap the link to open Google Maps.
        """.trimIndent()
        
        for (contact in contacts) {
            try {
                sendSMS(contact.phoneNumber, locationMessage)
                Log.d("SafetyService", "Location SMS sent to ${contact.name}: ${contact.phoneNumber}")
                
                kotlinx.coroutines.delay(500)
            } catch (e: Exception) {
                Log.e("SafetyService", "Failed to send location SMS to ${contact.name}: ${e.message}")
            }
        }
    }
    
    private fun buildEmergencyMessage(locationText: String): String {
        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        return """
            🚨 EMERGENCY SOS ALERT 🚨
            
            I AM IN DANGER! Please help me immediately!
            
            User: ${getUserName()}
            Time: $timestamp
            Location: $locationText
            
            This is an automated emergency message from Suraksha Safety App.
            Please respond or call me back immediately.
        """.trimIndent()
    }
    
    private fun getUserName(): String {
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
    
    private fun updateLocationNotification(location: Location) {
        val notification = createNotification(
            "Location Tracking Active",
            "Current: ${location.latitude}, ${location.longitude}"
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    private fun showLocationSharedNotification(contactName: String, location: Location?) {
        val locationText = if (location != null) {
            "Location shared with $contactName: ${location.latitude}, ${location.longitude}"
        } else {
            "Location shared with $contactName (location unavailable)"
        }
        
        val notification = createNotification("Location Shared", locationText)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID + 1, notification)
    }
    
    private fun getAppAuthority(): String {
        return applicationContext.packageName + ".provider"
    }

    private fun listRecordingFiles(): List<File> {
        val recordings = mutableListOf<File>()
        try {
            val externalDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC)
            val internalDir = filesDir
            val candidates = listOfNotNull(externalDir, internalDir)
            candidates.forEach { dir ->
                dir.listFiles()?.filter {
                    it.isFile && it.name.startsWith("emergency_recording_") && it.name.endsWith(".m4a")
                }?.let { recordings.addAll(it) }
            }
        } catch (e: Exception) {
            Log.e("SafetyService", "Error listing recordings: ${e.message}")
        }
        return recordings.sortedBy { it.lastModified() }
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
            val outputDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC) ?: filesDir
            recordingFile = File(outputDir, "emergency_recording_${System.currentTimeMillis()}.m4a")
            
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

            serviceScope.launch {
                try {
                    repository.setBooleanSetting("recording_active", true)
                    repository.setSetting("recording_started_at", System.currentTimeMillis().toString())
                } catch (e: Exception) {
                    Log.e("SafetyService", "Failed to persist recording state: ${e.message}")
                }
            }
            
            val notification = createNotification("Recording Active", "Emergency audio recording in progress")
            startForeground(NOTIFICATION_ID, notification)
            
        } catch (e: Exception) {
            Log.e("SafetyService", "Error starting recording: ${e.message}")
            isRecording = false
        }
    }
    
    private fun stopRecording() {
        if (!isRecording && mediaRecorder == null) {
            Log.d("SafetyService", "No recording in progress")
            return
        }
        
        try {
            mediaRecorder?.apply {
                try { stop() } catch (e: Exception) { Log.w("SafetyService", "stop() failed: ${e.message}") }
                try { release() } catch (e: Exception) { Log.w("SafetyService", "release() failed: ${e.message}") }
            }
            mediaRecorder = null
            isRecording = false
            isPaused = false
            
            Log.d("SafetyService", "Emergency recording stopped: ${recordingFile?.absolutePath}")
            
            recordingFile?.let { file ->
                if (file.exists()) {
                    val fileSizeKB = file.length() / 1024
                    Log.d("SafetyService", "Recording file saved successfully. Size: ${fileSizeKB}KB")
                } else {
                    Log.e("SafetyService", "Recording file does not exist after stopping")
                }
            }
            
            serviceScope.launch {
                try {
                    repository.addSafetyRecord(
                        type = "RECORDING",
                        latitude = null,
                        longitude = null,
                        recordingPath = recordingFile?.absolutePath
                    )
                    Log.d("SafetyService", "Recording path saved to database: ${recordingFile?.absolutePath}")
                    repository.setBooleanSetting("recording_active", false)
                    repository.setSetting("recording_started_at", "0")
                } catch (e: Exception) {
                    Log.e("SafetyService", "Failed to save recording record: ${e.message}")
                }
            }
            
            try { stopForeground(true) } catch (_: Exception) {}
            
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
        stopLocationTracking()
        serviceScope.cancel()
    }
}
