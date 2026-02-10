package com.example.simala// FIXED: Changed from com.vera.ui to com.example.vera.ui

import android.media.RingtoneManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd // Standard decline icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FakeCallScreen(
    callerName: String = "Home",
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    val ringtone = remember {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        RingtoneManager.getRingtone(context, uri)
    }

    // Cleanup ringtone when leaving screen
    DisposableEffect(Unit) {
        ringtone.play()
        onDispose {
            ringtone.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight().padding(vertical = 80.dp)
        ) {
            // 1. CALLER INFO
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = CircleShape,
                    color = Color.DarkGray
                ) { }
                Spacer(modifier = Modifier.height(24.dp))
                Text(callerName, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Light)
                Text("Mobile", color = Color.Gray, fontSize = 18.sp)
            }

            // 2. CALL CONTROLS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // DECLINE
                CallAction(
                    label = "Decline",
                    icon = Icons.Default.CallEnd,
                    color = Color.Red,
                    onClick = onDismiss
                )

                // ANSWER
                CallAction(
                    label = "Answer",
                    icon = Icons.Default.Call,
                    color = Color(0xFF4CAF50),
                    onClick = onDismiss
                )
            }
        }
    }
}

@Composable
private fun CallAction(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(72.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = color)
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(32.dp))
        }
        Text(label, color = Color.White, modifier = Modifier.padding(top = 8.dp))
    }
}