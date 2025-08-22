package com.example.suraksha.ui.screens

import android.content.Intent
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
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
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Settings",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Header with improved styling
            Text(
                text = "Configure Your Safety",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Customize your safety features and preferences",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Permission Status Section
            PermissionStatusSection()
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // Safety Features Section
            SettingsSection(
                title = "Safety Features",
                icon = Icons.Default.Security,
                description = "Configure emergency triggers and safety tools"
            ) {
                // Disguised Mode
                SettingsSwitch(
                    icon = Icons.Default.Lock,
                    title = "Disguised Mode",
                    subtitle = "Change app icon to calculator for privacy",
                    checked = mainViewModel.uiState.value.isDisguisedMode,
                    onCheckedChange = { enabled ->
                        mainViewModel.setDisguisedMode(enabled)
                    }
                )
                
                SettingsDivider()
                
                // Power Button Detection
                SettingsSwitch(
                    icon = Icons.Default.Power,
                    title = "Power Button SOS",
                    subtitle = "Press power button 4 times to trigger emergency",
                    checked = true, // Always enabled for now
                    onCheckedChange = { /* TODO: Implement */ }
                )
                
                SettingsDivider()
                
                // Voice Command SOS
                SettingsSwitch(
                    icon = Icons.Default.Mic,
                    title = "Voice Command SOS",
                    subtitle = "Trigger SOS with custom voice command",
                    checked = mainViewModel.uiState.value.isVoiceDetectionEnabled,
                    onCheckedChange = { mainViewModel.toggleVoiceDetection(it) }
                )
                
                if (mainViewModel.uiState.value.isVoiceDetectionEnabled) {
                    SettingsDivider()
                    
                    VoiceCommandSetting(
                        currentCommand = mainViewModel.getVoiceCommand(),
                        onCommandChange = { mainViewModel.setVoiceCommand(it) }
                    )
                }
                
                SettingsDivider()
                
                // Auto Recording
                SettingsSwitch(
                    icon = Icons.Default.FiberManualRecord,
                    title = "Auto Recording",
                    subtitle = "Record audio during SOS events",
                    checked = true, // Always enabled for now
                    onCheckedChange = { /* TODO: Implement */ }
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // Location Sharing Section
            SettingsSection(
                title = "Location Sharing",
                icon = Icons.Default.LocationOn,
                description = "Manage location sharing and accuracy settings"
            ) {
                SettingsItem(
                    icon = Icons.Default.GpsFixed,
                    title = "Location Accuracy",
                    subtitle = "High accuracy GPS for precise location sharing",
                    onClick = { /* TODO: Show location settings */ }
                )
                
                SettingsDivider()
                
                SettingsItem(
                    icon = Icons.Default.Share,
                    title = "Test Location Sharing",
                    subtitle = "Send test location to all emergency contacts",
                    onClick = { 
                        mainViewModel.shareLocationWithContacts()
                    }
                )
                
                SettingsDivider()
                
                SettingsItem(
                    icon = Icons.Default.PrivacyTip,
                    title = "Location Privacy",
                    subtitle = "Location only shared when you choose to share",
                    onClick = { /* TODO: Show privacy info */ }
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // Notifications Section
            SettingsSection(
                title = "Notifications",
                icon = Icons.Default.Notifications,
                description = "Configure alert preferences and notifications"
            ) {
                SettingsSwitch(
                    icon = Icons.Default.Emergency,
                    title = "Emergency Notifications",
                    subtitle = "Show notifications for safety events",
                    checked = true,
                    onCheckedChange = { /* TODO: Implement */ }
                )
                
                SettingsDivider()
                
                SettingsSwitch(
                    icon = Icons.Default.Vibration,
                    title = "Vibration Alerts",
                    subtitle = "Vibrate on SOS trigger",
                    checked = true,
                    onCheckedChange = { /* TODO: Implement */ }
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // Privacy Section
            SettingsSection(
                title = "Privacy & Security",
                icon = Icons.Default.Lock,
                description = "Data protection and security settings"
            ) {
                SettingsItem(
                    icon = Icons.Default.Storage,
                    title = "Data Storage",
                    subtitle = "All data stored locally on device",
                    onClick = { /* TODO: Show data info */ }
                )
                
                SettingsDivider()
                
                SettingsItem(
                    icon = Icons.Default.LocationOn,
                    title = "Location Accuracy",
                    subtitle = "High accuracy GPS for precise location",
                    onClick = { /* TODO: Show location settings */ }
                )
            }
            
            Spacer(modifier = Modifier.height(28.dp))
            
            // About Section
            SettingsSection(
                title = "About",
                icon = Icons.Default.Info,
                description = "App information and support"
            ) {
                SettingsItem(
                    icon = Icons.Default.Apps,
                    title = "App Version",
                    subtitle = "1.0.0",
                    onClick = { /* TODO: Show version info */ }
                )
                
                SettingsDivider()
                
                SettingsItem(
                    icon = Icons.Default.Help,
                    title = "Help & Support",
                    subtitle = "How to use the app",
                    onClick = { /* TODO: Show help */ }
                )
                
                SettingsDivider()
                
                SettingsItem(
                    icon = Icons.Default.Policy,
                    title = "Privacy Policy",
                    subtitle = "How we protect your data",
                    onClick = { /* TODO: Show privacy policy */ }
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Emergency Info Card with improved design
            EmergencyInfoCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Reset App State (for testing) with improved design
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Text(
                            text = "Reset App State",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Text(
                        text = "Reset onboarding and permission states for testing purposes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { showResetDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset App State")
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { 
                Text(
                    "Reset App State",
                    fontWeight = FontWeight.Bold
                )
            },
            text = { 
                Text(
                    "This will reset the app to its initial state. You'll see the onboarding screen again. Continue?"
                )
            },
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
    icon: ImageVector,
    description: String,
    content: @Composable () -> Unit
) {
    Column {
        // Section header with icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        // Section description
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Section content card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with background
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(
                    if (checked) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.surfaceVariant
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
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
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with background
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
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
fun SettingsDivider() {
    Divider(
        modifier = Modifier.padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 0.5.dp
    )
}

@Composable
fun EmergencyInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.error),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Emergency",
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Emergency Information",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "• SOS button sends your location to emergency contacts\n" +
                      "• Press power button 4 times to trigger SOS\n" +
                      "• Say custom voice command to trigger SOS\n" +
                      "• Fake call feature helps in uncomfortable situations\n" +
                      "• Timer automatically triggers SOS if not cancelled\n" +
                      "• Share location button sends live location via SMS\n" +
                      "• All data is stored locally for your privacy",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.4
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
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Voice Command",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Voice Command",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "\"$currentCommand\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 2.dp)
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
            title = { 
                Text(
                    "Set Voice Command",
                    fontWeight = FontWeight.Bold
                )
            },
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
    
    SettingsSection(
        title = "Permission Status",
        icon = Icons.Default.Security,
        description = "Monitor and manage app permissions"
    ) {
        permissionsByCategory.forEach { (category, permissions) ->
            val categoryInfo = getCategoryInfo(category)
            val grantedCount = permissions.count { it.status == PermissionStatus.GRANTED }
            val totalCount = permissions.size
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showPermissionDialog = true }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(
                            if (grantedCount == totalCount) 
                                MaterialTheme.colorScheme.primaryContainer 
                            else 
                                MaterialTheme.colorScheme.errorContainer
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = categoryInfo.icon,
                        contentDescription = categoryInfo.title,
                        tint = if (grantedCount == totalCount) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = categoryInfo.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "$grantedCount of $totalCount permissions granted",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
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
                SettingsDivider()
            }
        }
    }
    
    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { 
                Text(
                    "Permission Details",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    permissionsByCategory.forEach { (category, permissions) ->
                        val categoryInfo = getCategoryInfo(category)
                        
                        Text(
                            text = categoryInfo.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        permissions.forEach { permission ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
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
