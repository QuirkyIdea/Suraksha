package com.example.suraksha.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.suraksha.ui.viewmodels.MainViewModel
import com.example.suraksha.utils.*
import android.content.Context
import com.example.suraksha.ui.screens.OnboardingActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    var showResetDialog by remember { mutableStateOf(false) }
    var pickedFolderName by remember { mutableStateOf("") }
    val currentFolderUri = remember { mainViewModel.getRecordingDirUri() }
    LaunchedEffect(currentFolderUri) {
        currentFolderUri?.let {
            val doc = DocumentFile.fromTreeUri(context, Uri.parse(it))
            pickedFolderName = doc?.name ?: ""
        }
    }

    val folderPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree(),
        onResult = { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                mainViewModel.setRecordingDirUri(it.toString())
                val doc = DocumentFile.fromTreeUri(context, it)
                pickedFolderName = doc?.name ?: it.lastPathSegment.orEmpty()
            }
        }
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Permission Status Section
            PermissionStatusSection()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Safety Features Section
            SettingsSection(title = "Safety Features") {
                // Disguised Mode
                SettingsSwitch(
                    icon = Icons.Default.Lock,
                    title = "Disguised Mode",
                    subtitle = "Make app look like a calculator",
                    checked = mainViewModel.uiState.value.isDisguisedMode,
                    onCheckedChange = { mainViewModel.setDisguisedMode(it) }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                         // Power Button Detection
                SettingsSwitch(
                    icon = Icons.Default.Settings,
                    title = "Power Button SOS",
                    subtitle = "Press power button 4 times to trigger emergency",
                    checked = true, // Always enabled for now
                    onCheckedChange = { /* TODO: Implement */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Voice Command SOS
                SettingsSwitch(
                    icon = Icons.Default.PlayArrow,
                    title = "Voice Command SOS",
                    subtitle = "Trigger SOS with custom voice command",
                    checked = mainViewModel.uiState.value.isVoiceDetectionEnabled,
                    onCheckedChange = { mainViewModel.toggleVoiceDetection(it) }
                )
                
                if (mainViewModel.uiState.value.isVoiceDetectionEnabled) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    VoiceCommandSetting(
                        currentCommand = mainViewModel.getVoiceCommand(),
                        onCommandChange = { mainViewModel.setVoiceCommand(it) }
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                // Auto Recording
                SettingsSwitch(
                    icon = Icons.Default.Info,
                    title = "Auto Recording",
                    subtitle = "Record audio during SOS events",
                    checked = true, // Always enabled for now
                    onCheckedChange = { /* TODO: Implement */ }
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Notifications Section
            SettingsSection(title = "Notifications") {
                SettingsSwitch(
                    icon = Icons.Default.Notifications,
                    title = "Emergency Notifications",
                    subtitle = "Show notifications for safety events",
                    checked = true,
                    onCheckedChange = { /* TODO: Implement */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingsSwitch(
                    icon = Icons.Default.Notifications,
                    title = "Vibration Alerts",
                    subtitle = "Vibrate on SOS trigger",
                    checked = true,
                    onCheckedChange = { /* TODO: Implement */ }
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Privacy Section
            SettingsSection(title = "Privacy & Security") {
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Recording Storage Location",
                    subtitle = if (pickedFolderName.isNotEmpty()) pickedFolderName else "App Music folder (default)",
                    onClick = { folderPicker.launch(null) }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingsItem(
                    icon = Icons.Default.LocationOn,
                    title = "Location Accuracy",
                    subtitle = "High accuracy GPS for precise location",
                    onClick = { /* TODO: Show location settings */ }
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // About Section
            SettingsSection(title = "About") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "App Version",
                    subtitle = "1.0.0",
                    onClick = { /* TODO: Show version info */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Help & Support",
                    subtitle = "How to use the app",
                    onClick = { /* TODO: Show help */ }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Privacy Policy",
                    subtitle = "How we protect your data",
                    onClick = { /* TODO: Show privacy policy */ }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Emergency Info Card
            EmergencyInfoCard()

            // Reset App State (for testing)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Reset App State",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Reset onboarding and permission states for testing",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Button(
                        onClick = { showResetDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Reset App State")
                    }
                }
            }
        }
    }
    
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset App State") },
            text = { Text("This will reset the app to its initial state. You'll see the onboarding screen again. Continue?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Reset app state
                        val sharedPrefs = context.getSharedPreferences("suraksha_prefs", Context.MODE_PRIVATE)
                        sharedPrefs.edit().clear().apply()
                        showResetDialog = false
                        
                        // Restart the app
                        val intent = Intent(context, OnboardingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        context.startActivity(intent)
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun SettingsSwitch(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun EmergencyInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Emergency",
                    tint = MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Emergency Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
                         Text(
                 text = "• SOS button sends your location to emergency contacts\n" +
                       "• Press power button 3 times to trigger SOS\n" +
                       "• Say custom voice command to trigger SOS\n" +
                       "• Fake call feature helps in uncomfortable situations\n" +
                       "• Timer automatically triggers SOS if not cancelled\n" +
                       "• All data is stored locally for your privacy",
                 style = MaterialTheme.typography.bodySmall,
                 color = MaterialTheme.colorScheme.onErrorContainer
             )
        }
    }
}

@Composable
fun VoiceCommandSetting(
    currentCommand: String,
    onCommandChange: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempCommand by remember { mutableStateOf(currentCommand) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Voice Command",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Voice Command",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "\"$currentCommand\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Edit",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Set Voice Command") },
            text = {
                Column {
                    Text(
                        text = "Enter a custom phrase to trigger SOS. Keep it simple and easy to remember.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = tempCommand,
                        onValueChange = { tempCommand = it },
                        label = { Text("Voice Command") },
                        placeholder = { Text("emergency help me") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (tempCommand.trim().isNotEmpty()) {
                            onCommandChange(tempCommand.trim())
                            showDialog = false
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    tempCommand = currentCommand
                    showDialog = false 
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PermissionStatusSection() {
    val context = LocalContext.current
    val permissionsByCategory = remember { PermissionManager.getPermissionsByCategory(context) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    
    SettingsSection(title = "Permission Status") {
        permissionsByCategory.forEach { (category, permissions) ->
            val categoryInfo = getCategoryInfo(category)
            val grantedCount = permissions.count { it.status == PermissionStatus.GRANTED }
            val totalCount = permissions.size
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showPermissionDialog = true }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = categoryInfo.icon,
                    contentDescription = categoryInfo.title,
                    tint = if (grantedCount == totalCount) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = categoryInfo.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$grantedCount of $totalCount permissions granted",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "View Details",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            if (category != permissionsByCategory.keys.last()) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
    
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permission Details") },
            text = {
                Column {
                    permissionsByCategory.forEach { (category, permissions) ->
                        val categoryInfo = getCategoryInfo(category)
                        
                        Text(
                            text = categoryInfo.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        
                        permissions.forEach { permission ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = getStatusIcon(permission.status),
                                    contentDescription = permission.status.name,
                                    tint = getStatusColor(permission.status),
                                    modifier = Modifier.size(16.dp)
                                )
                                
                                Spacer(modifier = Modifier.width(8.dp))
                                
                                Text(
                                    text = getPermissionDisplayName(permission.permission),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        val intent = Intent(context, PermissionRequestActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text("Manage Permissions")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
