package com.example.simala.ui // FIXED: Matches your project package

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield // Import actual Shield icon
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VeraHomeScreen(
    onTriggerFakeCall: () -> Unit
) {
    var isSafeWalkActive by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderSection(isSafeWalkActive)

            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                if (!isSafeWalkActive) {
                    StartWalkButton { isSafeWalkActive = true }
                } else {
                    ActiveMonitoringView(
                        onCancel = { isSafeWalkActive = false },
                        onEmergency = { /* Future: Call Python API */ },
                        onFakeCallRequest = onTriggerFakeCall
                    )
                }
            }

            Text(
                text = if (isSafeWalkActive) "Vera is encrypted and watching" else "Your companion for the journey",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun HeaderSection(isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(40.dp))
        Icon(
            // FIXED: Removed the TODO() property and used standard Shield icon
            imageVector = Icons.Default.Shield,
            contentDescription = "Vera Logo",
            tint = if (isActive) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "VERA",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun StartWalkButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(240.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("START", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            Text("SAFE WALK", fontSize = 16.sp)
        }
    }
}

@Composable
fun ActiveMonitoringView(
    onCancel: () -> Unit,
    onEmergency: () -> Unit,
    onFakeCallRequest: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onEmergency,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = CircleShape,
            modifier = Modifier.size(200.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Text("SOS", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onFakeCallRequest) {
            Text("NEED A REASON TO LEAVE? (FAKE CALL)", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("I'M SAFE / END TRIP")
        }
    }
}