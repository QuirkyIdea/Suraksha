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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.example.suraksha.ui.theme.SOSRed
import com.example.suraksha.ui.viewmodels.MainViewModel
import com.example.suraksha.ui.components.ErrorCard
import com.example.suraksha.ui.components.QuoteCard
import com.example.suraksha.data.QuotesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.suraksha.R

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
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Enhanced Header Section
        HeaderSection()
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Location Status Card with improved design
        LocationStatusCard(uiState.lastKnownLocation)
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Main SOS Button with enhanced styling
        SOSButton(
            isTriggered = uiState.isSOSTriggered,
            onSOSTrigger = { mainViewModel.triggerSOS() }
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Quick Actions with improved layout
        QuickActionsSection(
            mainViewModel = mainViewModel,
            onFakeCall = onNavigateToFakeCall,
            onTimer = { minutes -> mainViewModel.startTimer(minutes) },
            onRecording = { /* Will be handled inside QuickActionsSection */ }
        )
        
        Spacer(modifier = Modifier.height(28.dp))
        
        // Timer Section with enhanced design
        if (timerState.isActive) {
            TimerSection(
                timerState = timerState,
                onCancelTimer = { mainViewModel.cancelTimer() }
            )
            Spacer(modifier = Modifier.height(28.dp))
        }
        
        // Emergency Contacts Status with improved design
        EmergencyContactsStatus(emergencyContacts)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Daily Quote Card
        val dailyQuote = remember { QuotesManager.getDailyQuote() }
        QuoteCard(quote = dailyQuote)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Voice Detection Status with enhanced styling
        if (uiState.isVoiceDetectionEnabled && mainViewModel.isVoiceDetectionActive()) {
            VoiceDetectionCard(mainViewModel.getVoiceCommand())
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Error Messages
        uiState.errorMessage?.let { error ->
            ErrorCard(message = error, onDismiss = { mainViewModel.clearError() })
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Title with enhanced styling
        Text(
            text = "Suraksha Safety",
            style = MaterialTheme.typography.displayMedium,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Subtitle with improved typography
        Text(
            text = buildAnnotatedString {
                append("Your ")
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append("safety")
                pop()
                append(" is our priority.")
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun LocationStatusCard(location: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Current Location",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
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
            .size(220.dp)
            .scale(scale * pulseAnimation),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(180.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer glow effect when triggered
                if (isTriggered) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(
                                MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                            )
                    )
                }
                
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .clickable {
                            scope.launch {
                                onSOSTrigger()
                            }
                        }
                        .background(
                            if (isTriggered) MaterialTheme.colorScheme.error.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.red_bell_modified),
                        contentDescription = if (isTriggered) "SOS Sent" else "SOS Button",
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            
            if (isTriggered) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "SOS SENT!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SOSRed
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
        modifier = Modifier.fillMaxWidth()
    ) {
        // Section Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bolt,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        // Action Grid
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // First Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.Phone,
                    label = "Fake Call",
                    description = "Simulate incoming call",
                    onClick = onFakeCall,
                    modifier = Modifier.weight(1f)
                )
                
                QuickActionButton(
                    icon = Icons.Default.Timer,
                    label = "Safety Timer",
                    description = "Set countdown timer",
                    onClick = { showTimerDialog = true },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Second Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.FiberManualRecord,
                    label = if (mainViewModel.uiState.collectAsStateWithLifecycle().value.isRecordingActive) {
                        val elapsedMs = mainViewModel.uiState.collectAsStateWithLifecycle().value.recordingElapsedMs
                        val seconds = (elapsedMs / 1000) % 60
                        val minutes = (elapsedMs / 1000) / 60
                        String.format("Rec %02d:%02d", minutes, seconds)
                    } else "Record",
                    description = "Emergency recording",
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
                        // refresh state to start timer
                        mainViewModel.refreshRecordingState()
                    },
                    modifier = Modifier.weight(1f)
                )
                
                QuickActionButton(
                    icon = Icons.Default.Power,
                    label = "Power SOS",
                    description = "4x power button",
                    onClick = { showPowerSOSDialog = true },
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Third Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.Mic,
                    label = "Voice SOS",
                    description = "Voice command trigger",
                    onClick = { showVoiceSOSDialog = true },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    
    // Timer Selection Dialog
    if (showTimerDialog) {
        AlertDialog(
            onDismissRequest = { showTimerDialog = false },
            title = { 
                Text(
                    "Set Safety Timer",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Choose how long you want the safety timer to run. If you don't cancel it in time, an SOS alert will be sent automatically.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val timerOptions = listOf(1, 2, 5, 10, 15, 30)
                    timerOptions.forEach { minutes ->
                        OutlinedButton(
                            onClick = {
                                onTimer(minutes)
                                showTimerDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Text("$minutes minutes")
                        }
                    }
                }
            },
            confirmButton = {
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
            title = { 
                Text(
                    "Recording Controls",
                    fontWeight = FontWeight.Bold
                )
            },
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
            title = { 
                Text(
                    "Power Button SOS",
                    fontWeight = FontWeight.Bold
                )
            },
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
            title = { 
                Text(
                    "Edit Voice Command SOS",
                    fontWeight = FontWeight.Bold
                )
            },
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
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Timer",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Safety Timer Active",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val minutes = timerState.remainingSeconds / 60
            val seconds = timerState.remainingSeconds % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = onCancelTimer,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
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
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Contacts,
                    contentDescription = "Emergency Contacts",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Emergency Contacts",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (contacts.isEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "No contacts",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "No emergency contacts added",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            } else {
                contacts.forEach { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = contact.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = contact.phoneNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceDetectionCard(voiceCommand: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Detection Active",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Voice SOS Active",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Say \"$voiceCommand\" to trigger SOS",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}


