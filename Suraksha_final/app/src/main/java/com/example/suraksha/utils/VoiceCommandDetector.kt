package com.example.suraksha.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.*
import java.util.*

class VoiceCommandDetector(private val context: Context) {
    
    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false
    private var customCommand = "emergency help me"
    private var onVoiceCommandListener: (() -> Unit)? = null
    private var onErrorListener: ((String) -> Unit)? = null
    private val detectorScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    fun setCustomCommand(command: String) {
        customCommand = command.lowercase().trim()
        Log.d("VoiceCommandDetector", "Custom command set to: '$customCommand'")
    }
    
    fun getCustomCommand(): String {
        return customCommand
    }
    
    fun setOnVoiceCommandListener(listener: () -> Unit) {
        onVoiceCommandListener = listener
    }
    
    fun setOnErrorListener(listener: (String) -> Unit) {
        onErrorListener = listener
    }
    
    fun startListening() {
        if (isListening) {
            Log.d("VoiceCommandDetector", "Already listening")
            return
        }
        
        if (!PermissionManager.hasAudioPermission(context)) {
            Log.w("VoiceCommandDetector", "Audio permission not granted")
            onErrorListener?.invoke("Audio permission not granted")
            return
        }
        
        try {
            if (speechRecognizer == null) {
                speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
                speechRecognizer?.setRecognitionListener(createRecognitionListener())
            }
            
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
                putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            }
            
            speechRecognizer?.startListening(intent)
            isListening = true
            
            Log.d("VoiceCommandDetector", "Started listening for voice command: '$customCommand'")
            
        } catch (e: Exception) {
            Log.e("VoiceCommandDetector", "Error starting speech recognition: ${e.message}")
            onErrorListener?.invoke("Failed to start voice recognition: ${e.message}")
        }
    }
    
    fun stopListening() {
        if (!isListening) return
        
        try {
            speechRecognizer?.stopListening()
            isListening = false
            Log.d("VoiceCommandDetector", "Stopped listening")
        } catch (e: Exception) {
            Log.e("VoiceCommandDetector", "Error stopping speech recognition: ${e.message}")
        }
    }
    
    fun isCurrentlyListening(): Boolean {
        return isListening
    }
    
    private fun createRecognitionListener(): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("VoiceCommandDetector", "Ready for speech")
            }
            
            override fun onBeginningOfSpeech() {
                Log.d("VoiceCommandDetector", "Beginning of speech")
            }
            
            override fun onRmsChanged(rmsdB: Float) {
            }
            
            override fun onBufferReceived(buffer: ByteArray?) {
            }
            
            override fun onEndOfSpeech() {
                Log.d("VoiceCommandDetector", "End of speech")
            }
            
            override fun onError(error: Int) {
                isListening = false
                val errorMessage = when (error) {
                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                    SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                    SpeechRecognizer.ERROR_NETWORK -> "Network error"
                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                    SpeechRecognizer.ERROR_NO_MATCH -> "No speech match"
                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
                    SpeechRecognizer.ERROR_SERVER -> "Server error"
                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Speech timeout"
                    else -> "Unknown error: $error"
                }
                
                Log.w("VoiceCommandDetector", "Speech recognition error: $errorMessage")
                
                if (error != SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                    detectorScope.launch {
                        delay(2000)
                        if (PermissionManager.hasAudioPermission(context)) {
                            startListening()
                        }
                    }
                } else {
                    onErrorListener?.invoke(errorMessage)
                }
            }
            
            override fun onResults(results: Bundle?) {
                isListening = false
                
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches.isNullOrEmpty()) {
                    Log.d("VoiceCommandDetector", "No speech results")
                    restartListening()
                    return
                }
                
                val spokenText = matches[0].lowercase().trim()
                Log.d("VoiceCommandDetector", "Heard: '$spokenText', Looking for: '$customCommand'")
                
                if (spokenText.contains(customCommand, ignoreCase = true)) {
                    Log.d("VoiceCommandDetector", "Voice command detected! Triggering SOS")
                    onVoiceCommandListener?.invoke()
                } else {
                    Log.d("VoiceCommandDetector", "Voice command not detected, continuing to listen")
                    restartListening()
                }
            }
            
            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val partialText = matches[0].lowercase().trim()
                    Log.d("VoiceCommandDetector", "Partial: '$partialText'")
                    
                    if (partialText.contains(customCommand, ignoreCase = true)) {
                        Log.d("VoiceCommandDetector", "Voice command detected in partial results!")
                        onVoiceCommandListener?.invoke()
                    }
                }
            }
            
            override fun onEvent(eventType: Int, params: Bundle?) {
            }
        }
    }
    
    private fun restartListening() {
        detectorScope.launch {
            delay(1000)
            if (PermissionManager.hasAudioPermission(context)) {
                startListening()
            }
        }
    }
    
    fun destroy() {
        try {
            stopListening()
            speechRecognizer?.destroy()
            speechRecognizer = null
            detectorScope.cancel()
            Log.d("VoiceCommandDetector", "Voice command detector destroyed")
        } catch (e: Exception) {
            Log.e("VoiceCommandDetector", "Error destroying voice command detector: ${e.message}")
        }
    }
}

