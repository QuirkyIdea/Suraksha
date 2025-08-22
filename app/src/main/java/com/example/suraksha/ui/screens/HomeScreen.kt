package com.example.suraksha.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.example.suraksha.ui.theme.SOSRed
import com.example.suraksha.ui.viewmodels.MainViewModel
import com.example.suraksha.ui.components.ErrorCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onNavigateToFakeCall: () -> Unit
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
    val timerState by mainViewModel.timerState.collectAsStateWithLifecycle()
    val emergencyContacts by mainViewModel.emergencyContacts.collectAsStateWithLifecycle()
    
    val scope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Suraksha Safety",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Your safety is our priority",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Location Status Card
        LocationStatusCard(uiState.lastKnownLocation)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Main SOS Button
        SOSButton(
            isTriggered = uiState.isSOSTriggered,
            onSOSTrigger = { mainViewModel.triggerSOS() }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Quick Actions---------------------------------------
        QuickActionsSection(
            mainViewModel = mainViewModel,
            onFakeCall = onNavigateToFakeCall,
            onTimer = { minutes -> mainViewModel.startTimer(minutes) },
            onRecording = { /* Will be handled inside QuickActionsSection */ }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Timer Section-----------------------------------------
        if (timerState.isActive) {
            TimerSection(
                timerState = timerState,
                onCancelTimer = { mainViewModel.cancelTimer() }
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Emergency Contacts Status
        EmergencyContactsStatus(emergencyContacts)
        
        // Error/Success Messages
        // Voice Detection Status
        if (uiState.isVoiceDetectionEnabled && mainViewModel.isVoiceDetectionActive()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Voice Detection Active",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = "Voice SOS Active - Say \"${mainViewModel.getVoiceCommand()}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        uiState.errorMessage?.let { error ->
            ErrorCard(message = error, onDismiss = { mainViewModel.clearError() })
        }
    }
}

@Composable
fun LocationStatusCard(location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location",
                tint = MaterialTheme.colorScheme.primary
            )
            
            Column {
                Text(
                    text = "Current Location",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun SOSButton(
    isTriggered: Boolean,
    onSOSTrigger: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scale by animateFloatAsState(
        targetValue = if (isTriggered) 1.1f else 1f,
        animationSpec = tween(200),
        label = "sos_scale"
    )
    
    val pulseAnimation by rememberInfiniteTransition(label = "sos_pulse").animateFloat(
        initialValue = 1f,
        targetValue = if (isTriggered) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sos_pulse"
    )
    
    Box(
        modifier = Modifier
            .size(200.dp)
            .scale(scale * pulseAnimation),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                scope.launch {
                    onSOSTrigger()
                }
            },
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isTriggered) Color.White else SOSRed
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 4.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🚨",
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isTriggered) "SOS SENT!" else "SOS",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isTriggered) SOSRed else Color.White
                )
            }
        }
    }
}

@Composable
fun QuickActionsSection(
    mainViewModel: MainViewModel,
    onFakeCall: () -> Unit,
    onTimer: (Int) -> Unit,
    onRecording: () -> Unit
) {
    var showTimerDialog by remember { mutableStateOf(false) }
    var showPowerSOSDialog by remember { mutableStateOf(false) }
    var showVoiceSOSDialog by remember { mutableStateOf(false) }
    var showRecordingDialog by remember { mutableStateOf(false) }
    var isRecordingPaused by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                icon = Icons.Default.Phone,
                label = "Fake Call",
                onClick = onFakeCall,
                modifier = Modifier.weight(1f)
            )
            
            QuickActionButton(
                icon = Icons.Default.Info,
                label = "Timer",
                onClick = { showTimerDialog = true },
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                icon = Icons.Default.Info,
                label = "Record",
                onClick = { 
                    // Start emergency recording
                    val intent = Intent(context, com.example.suraksha.services.SafetyService::class.java).apply {
                        action = com.example.suraksha.services.SafetyService.ACTION_START_RECORDING
                    }
                    context.startService(intent)
                    
                    // Show user feedback
                    android.widget.Toast.makeText(
                        context, 
                        "Emergency recording started", 
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    showRecordingDialog = true
                    isRecordingPaused = false
                },
                modifier = Modifier.weight(1f)
            )
            
            QuickActionButton(
                icon = Icons.Default.Settings,
                label = "Power SOS",
                onClick = { showPowerSOSDialog = true },
                modifier = Modifier.weight(1f)
            )
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionButton(
                icon = Icons.Default.PlayArrow,
                label = "Voice SOS",
                onClick = { showVoiceSOSDialog = true },
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    
    // Timer Selection Dialog
    if (showTimerDialog) {
        AlertDialog(
            onDismissRequest = { showTimerDialog = false },
            title = { Text("Set Safety Timer") },
            text = {
                Column {
                    Text(
                        text = "Choose how long you want the safety timer to run. If you don't cancel it in time, an SOS alert will be sent automatically.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Column {
                    val timerOptions = listOf(5, 10, 15, 30)
                    timerOptions.forEach { minutes ->
                        TextButton(
                            onClick = {
                                onTimer(minutes)
                                showTimerDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("$minutes minutes")
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimerDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Recording Controls Dialog
    if (showRecordingDialog) {
        AlertDialog(
            onDismissRequest = {
                // Do not stop recording on outside dismiss; require explicit Stop
                showRecordingDialog = false
            },
            title = { Text("Recording Controls") },
            text = {
                Column {
                    Text(
                        text = if (isRecordingPaused) "Recording is paused." else "Recording in progress...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(
                        onClick = {
                            if (isRecordingPaused) {
                                val intent = Intent(context, com.example.suraksha.services.SafetyService::class.java).apply {
                                    action = com.example.suraksha.services.SafetyService.ACTION_RESUME_RECORDING
                                }
                                context.startService(intent)
                                isRecordingPaused = false
                            } else {
                                val intent = Intent(context, com.example.suraksha.services.SafetyService::class.java).apply {
                                    action = com.example.suraksha.services.SafetyService.ACTION_PAUSE_RECORDING
                                }
                                context.startService(intent)
                                isRecordingPaused = true
                            }
                        }
                    ) {
                        Text(if (isRecordingPaused) "Resume" else "Pause")
                    }
                    TextButton(
                        onClick = {
                            val intent = Intent(context, com.example.suraksha.services.SafetyService::class.java).apply {
                                action = com.example.suraksha.services.SafetyService.ACTION_STOP_RECORDING
                            }
                            context.startService(intent)
                            android.widget.Toast.makeText(
                                context,
                                "Recording stopped",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                            showRecordingDialog = false
                            isRecordingPaused = false
                        }
                    ) {
                        Text("Stop")
                    }
                }
            }
        )
    }
    
    // Power SOS Info Dialog
    if (showPowerSOSDialog) {
        AlertDialog(
            onDismissRequest = { showPowerSOSDialog = false },
            title = { Text("Power Button SOS") },
            text = {
                Column {
                    Text(
                        text = "Press the power button 4 times quickly to trigger an emergency SOS alert.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This feature works even when the app is in the background or the screen is locked.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPowerSOSDialog = false }) {
                    Text("Got it!")
                }
            }
        )
    }
    
    // Voice SOS Edit Dialog
    if (showVoiceSOSDialog) {
        var draft by remember { mutableStateOf("") }
        LaunchedEffect(Unit) { draft = mainViewModel.getVoiceCommand() }
        AlertDialog(
            onDismissRequest = { showVoiceSOSDialog = false },
            title = { Text("Edit Voice Command SOS") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Set the phrase that triggers SOS hands-free.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    OutlinedTextField(
                        value = draft,
                        onValueChange = { draft = it },
                        singleLine = true,
                        label = { Text("Voice command") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    mainViewModel.setVoiceCommand(draft)
                    showVoiceSOSDialog = false
                    android.widget.Toast.makeText(
                        context,
                        "Voice command updated",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showVoiceSOSDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(80.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TimerSection(
    timerState: com.example.suraksha.ui.viewmodels.TimerState,
    onCancelTimer: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Safety Timer Active",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            val minutes = timerState.remainingSeconds / 60
            val seconds = timerState.remainingSeconds % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onCancelTimer,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.Close, contentDescription = "Cancel")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancel Timer")
            }
        }
    }
}

@Composable
fun EmergencyContactsStatus(contacts: List<com.example.suraksha.data.EmergencyContact>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Emergency Contacts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (contacts.isEmpty()) {
                Text(
                    text = "No emergency contacts added",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            } else {
                contacts.forEach { contact ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = contact.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = contact.phoneNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
    }
}


