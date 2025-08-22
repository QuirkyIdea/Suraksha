package com.example.suraksha.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingsScreen() {
    val context = LocalContext.current
    var recordings by remember { mutableStateOf(listRecordingFiles(context.cacheDir, context.getExternalFilesDir(Environment.DIRECTORY_MUSIC))) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recordings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        if (recordings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No recordings yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(recordings) { file ->
                    RecordingItem(
                        file = file,
                        onShare = {
                            val uri = FileProvider.getUriForFile(
                                context,
                                context.packageName + ".provider",
                                file
                            )
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "audio/*"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(Intent.createChooser(intent, "Share recording"))
                        },
                        onDelete = {
                            if (file.delete()) {
                                recordings = listRecordingFiles(context.cacheDir, context.getExternalFilesDir(Environment.DIRECTORY_MUSIC))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RecordingItem(file: File, onShare: () -> Unit, onDelete: () -> Unit) {
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()) }
    val dateText = remember(file) { sdf.format(Date(file.lastModified())) }
    val sizeKb = remember(file) { (file.length() / 1024).coerceAtLeast(1) }

    ListItem(
        headlineContent = { Text(file.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        supportingContent = { Text("$dateText  •  ${sizeKb}KB") },
        trailingContent = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onShare) {
                    Icon(Icons.Default.Share, contentDescription = "Share")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
    Divider()
}

private fun listRecordingFiles(internalDir: File, externalMusic: File?): List<File> {
    val out = mutableListOf<File>()
    listOfNotNull(externalMusic, internalDir).forEach { dir ->
        dir.listFiles()?.filter {
            it.isFile && it.name.startsWith("emergency_recording_") && it.name.endsWith(".m4a")
        }?.let { out.addAll(it) }
    }
    return out.sortedByDescending { it.lastModified() }
}


