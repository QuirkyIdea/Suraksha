package com.example.suraksha.ui.viewmodels

import android.app.Application
import android.content.Intent
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.suraksha.data.EmergencyContact
import com.example.suraksha.data.SurakshaRepository
import com.example.suraksha.services.SafetyService
import com.example.suraksha.utils.PermissionManager
import com.example.suraksha.utils.PowerButtonDetector
import com.example.suraksha.utils.VoiceCommandDetector
import com.example.suraksha.SurakshaApplication
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = SurakshaRepository(
        SurakshaApplication.database.emergencyContactDao(),
        SurakshaApplication.database.appSettingsDao(),
        SurakshaApplication.database.safetyRecordDao()
    )
    
    // Contacts ViewModel
    val contactsViewModel = ContactsViewModel(application)
    
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    
    // Power Button Detector for SOS
    private val powerButtonDetector = PowerButtonDetector(application)
    
    // Voice Command Detector for SOS
    private val voiceCommandDetector = VoiceCommandDetector(application)
    
    // UI State
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    // Emergency contacts
    val emergencyContacts = repository.getActiveContacts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    // Location state
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()
    
    // Timer state
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()
    
    init {
        viewModelScope.launch {
            // Check if user has completed onboarding
            val hasCompletedOnboarding = repository.getBooleanSetting("onboarding_completed", false)
            _uiState.value = _uiState.value.copy(hasCompletedOnboarding = hasCompletedOnboarding)
            
            // Check if disguised mode is enabled
            val isDisguisedMode = repository.getBooleanSetting("disguised_mode", false)
            _uiState.value = _uiState.value.copy(isDisguisedMode = isDisguisedMode)
            
            // Check if voice detection is enabled
            val isVoiceDetectionEnabled = repository.getBooleanSetting("voice_detection_enabled", true)
            _uiState.value = _uiState.value.copy(isVoiceDetectionEnabled = isVoiceDetectionEnabled)
            
            // Start location updates if permission granted
            if (PermissionManager.hasLocationPermission(getApplication())) {
                startLocationUpdates()
            }
            
            // Start power button detection for SOS
            startPowerButtonDetection()
            
            // Start voice command detection for SOS
            startVoiceCommandDetection()
        }
    }
    
    fun triggerSOS() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSOSTriggered = true)
                
                // Start safety service
                val intent = Intent(getApplication(), SafetyService::class.java).apply {
                    action = SafetyService.ACTION_SOS
                }
                getApplication<Application>().startService(intent)
                
                // Record the event
                repository.addSafetyRecord(
                    type = "SOS",
                    latitude = _currentLocation.value?.latitude,
                    longitude = _currentLocation.value?.longitude
                )
                
                // Reset after 5 seconds
                kotlinx.coroutines.delay(5000)
                _uiState.value = _uiState.value.copy(isSOSTriggered = false)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSOSTriggered = false,
                    errorMessage = "Failed to trigger SOS: ${e.message}"
                )
            }
        }
    }
    
    fun startLocationUpdates() {
        if (!PermissionManager.hasLocationPermission(getApplication())) return
        
        try {
            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .build()
            
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        _currentLocation.value = location
                        _uiState.value = _uiState.value.copy(
                            lastKnownLocation = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                        )
                    }
                }
            }
            
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                getApplication<Application>().mainLooper
            )
            
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Failed to start location updates: ${e.message}"
            )
        }
    }
    
    fun startTimer(minutes: Int) {
        viewModelScope.launch {
            val endTime = LocalDateTime.now().plusMinutes(minutes.toLong())
            _timerState.value = TimerState(
                isActive = true,
                endTime = endTime,
                totalMinutes = minutes
            )
            
            // Record timer start
            repository.addSafetyRecord(type = "TIMER_START")
            
            // Start countdown
            while (_timerState.value.isActive && LocalDateTime.now().isBefore(endTime)) {
                kotlinx.coroutines.delay(1000) // Update every second
                val remainingSeconds = java.time.Duration.between(LocalDateTime.now(), endTime).seconds
                _timerState.value = _timerState.value.copy(remainingSeconds = remainingSeconds)
            }
            
            // Timer expired - trigger SOS
            if (_timerState.value.isActive) {
                triggerSOS()
                _timerState.value = TimerState() // Reset timer
            }
        }
    }
    
    fun cancelTimer() {
        _timerState.value = TimerState()
        viewModelScope.launch {
            repository.addSafetyRecord(type = "TIMER_CANCELLED")
        }
    }
    
    fun setDisguisedMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.setBooleanSetting("disguised_mode", enabled)
            _uiState.value = _uiState.value.copy(isDisguisedMode = enabled)
        }
    }
    
    fun completeOnboarding() {
        viewModelScope.launch {
            repository.setBooleanSetting("onboarding_completed", true)
            _uiState.value = _uiState.value.copy(hasCompletedOnboarding = true)
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    private fun startPowerButtonDetection() {
        powerButtonDetector.setOnPowerButtonListener {
            triggerSOS()
        }
        powerButtonDetector.startListening()
    }
    
    private fun startVoiceCommandDetection() {
        viewModelScope.launch {
            // Load custom voice command from settings
            val customCommand = repository.getSetting("voice_command") ?: "emergency help me"
            voiceCommandDetector.setCustomCommand(customCommand)
            
            voiceCommandDetector.setOnVoiceCommandListener {
                triggerSOS()
            }
            
            voiceCommandDetector.setOnErrorListener { error ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Voice recognition error: $error"
                )
            }
            
            // Start voice detection if permission granted
            if (PermissionManager.hasAudioPermission(getApplication())) {
                voiceCommandDetector.startListening()
            }
        }
    }
    
    fun setVoiceCommand(command: String) {
        viewModelScope.launch {
            repository.setSetting("voice_command", command)
            voiceCommandDetector.setCustomCommand(command)
        }
    }
    
    fun getVoiceCommand(): String {
        return voiceCommandDetector.getCustomCommand()
    }

    fun setRecordingDirUri(uriString: String) {
        viewModelScope.launch {
            repository.setSetting("recording_dir_uri", uriString)
        }
    }

    fun getRecordingDirUri(): String? {
        return try {
            kotlinx.coroutines.runBlocking { repository.getSetting("recording_dir_uri") }
        } catch (_: Exception) {
            null
        }
    }
    
    fun toggleVoiceDetection(enabled: Boolean) {
        viewModelScope.launch {
            repository.setBooleanSetting("voice_detection_enabled", enabled)
            if (enabled && PermissionManager.hasAudioPermission(getApplication())) {
                voiceCommandDetector.startListening()
            } else {
                voiceCommandDetector.stopListening()
            }
            _uiState.value = _uiState.value.copy(isVoiceDetectionEnabled = enabled)
        }
    }
    
    fun isVoiceDetectionActive(): Boolean {
        return voiceCommandDetector.isCurrentlyListening()
    }
    
    override fun onCleared() {
        super.onCleared()
        fusedLocationClient.removeLocationUpdates(object : LocationCallback() {})
        powerButtonDetector.stopListening()
        voiceCommandDetector.destroy()
    }
}

data class MainUiState(
    val hasCompletedOnboarding: Boolean = false,
    val isDisguisedMode: Boolean = false,
    val isSOSTriggered: Boolean = false,
    val lastKnownLocation: String = "Location not available",
    val errorMessage: String? = null,
    val isVoiceDetectionEnabled: Boolean = false
)

data class TimerState(
    val isActive: Boolean = false,
    val endTime: LocalDateTime? = null,
    val totalMinutes: Int = 0,
    val remainingSeconds: Long = 0
)
