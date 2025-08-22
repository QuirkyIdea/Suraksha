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
import com.example.suraksha.utils.IconManager
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
    
    val contactsViewModel = ContactsViewModel(application)
    
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    
    private val powerButtonDetector = PowerButtonDetector(application)
    
    private val voiceCommandDetector = VoiceCommandDetector(application)
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    val emergencyContacts = repository.getActiveContacts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation: StateFlow<Location?> = _currentLocation.asStateFlow()
    
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()
    
    init {
        viewModelScope.launch {
            val hasCompletedOnboarding = repository.getBooleanSetting("onboarding_completed", false)
            _uiState.value = _uiState.value.copy(hasCompletedOnboarding = hasCompletedOnboarding)
            
            val isDisguisedMode = repository.getBooleanSetting("disguised_mode", false)
            _uiState.value = _uiState.value.copy(isDisguisedMode = isDisguisedMode)
            
            val isVoiceDetectionEnabled = repository.getBooleanSetting("voice_detection_enabled", true)
            _uiState.value = _uiState.value.copy(isVoiceDetectionEnabled = isVoiceDetectionEnabled)
            
            if (PermissionManager.hasLocationPermission(getApplication())) {
                startLocationUpdates()
            }
            
            startPowerButtonDetection()
            
            startVoiceCommandDetection()

            val active = repository.getBooleanSetting("recording_active", false)
            val startedAt = repository.getSetting("recording_started_at")?.toLongOrNull() ?: 0L
            _uiState.value = _uiState.value.copy(
                isRecordingActive = active,
                recordingElapsedMs = if (active && startedAt > 0) System.currentTimeMillis() - startedAt else 0
            )
            if (active) startRecordingTicker()
        }
    }

    private fun startRecordingTicker() {
        viewModelScope.launch {
            while (_uiState.value.isRecordingActive) {
                val startedAt = repository.getSetting("recording_started_at")?.toLongOrNull() ?: 0L
                val elapsed = if (startedAt > 0) System.currentTimeMillis() - startedAt else 0
                _uiState.value = _uiState.value.copy(recordingElapsedMs = elapsed)
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    fun refreshRecordingState() {
        viewModelScope.launch {
            val active = repository.getBooleanSetting("recording_active", false)
            val startedAt = repository.getSetting("recording_started_at")?.toLongOrNull() ?: 0L
            _uiState.value = _uiState.value.copy(
                isRecordingActive = active,
                recordingElapsedMs = if (active && startedAt > 0) System.currentTimeMillis() - startedAt else 0
            )
            if (active) startRecordingTicker()
        }
    }
    
    fun triggerSOS() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isSOSTriggered = true)
                
                val intent = Intent(getApplication(), SafetyService::class.java).apply {
                    action = SafetyService.ACTION_SOS
                }
                getApplication<Application>().startService(intent)
                
                repository.addSafetyRecord(
                    type = "SOS",
                    latitude = _currentLocation.value?.latitude,
                    longitude = _currentLocation.value?.longitude
                )
                
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
            
            repository.addSafetyRecord(type = "TIMER_START")
            
            while (_timerState.value.isActive && LocalDateTime.now().isBefore(endTime)) {
                kotlinx.coroutines.delay(1000)
                val remainingSeconds = java.time.Duration.between(LocalDateTime.now(), endTime).seconds
                _timerState.value = _timerState.value.copy(remainingSeconds = remainingSeconds)
            }
            
            if (_timerState.value.isActive) {
                triggerSOS()
                _timerState.value = TimerState()
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
            
            if (enabled) {
                IconManager.setCalculatorIcon(getApplication())
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Disguised mode enabled! App icon changed to calculator."
                )
            } else {
                IconManager.setNormalIcon(getApplication())
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Disguised mode disabled! App icon restored."
                )
            }
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
    
    fun shareLocationWithContacts() {
        viewModelScope.launch {
            try {
                val contacts = repository.getActiveContacts().first()
                if (contacts.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "No emergency contacts found. Please add contacts first."
                    )
                    return@launch
                }
                
                val location = _currentLocation.value
                if (location == null) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Location not available. Please ensure location permissions are granted."
                    )
                    return@launch
                }
                
                contacts.forEach { contact ->
                    val intent = Intent(getApplication(), SafetyService::class.java).apply {
                        action = SafetyService.ACTION_SEND_LOCATION_SMS
                        putExtra("phone_number", contact.phoneNumber)
                        putExtra("contact_name", contact.name)
                    }
                    getApplication<Application>().startService(intent)
                }
                
                repository.addSafetyRecord(
                    type = "LOCATION_SHARED",
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Location shared with ${contacts.size} contacts successfully!"
                )
                
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(errorMessage = null)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to share location: ${e.message}"
                )
            }
        }
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
    val isVoiceDetectionEnabled: Boolean = false,
    val isRecordingActive: Boolean = false,
    val recordingElapsedMs: Long = 0
)

data class TimerState(
    val isActive: Boolean = false,
    val endTime: LocalDateTime? = null,
    val totalMinutes: Int = 0,
    val remainingSeconds: Long = 0
)
