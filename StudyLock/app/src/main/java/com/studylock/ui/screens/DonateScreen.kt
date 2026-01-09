package com.studylock.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studylock.ui.components.StudyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Support Development") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Keep StudyLock Free & Open Source",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
            
            StudyCard {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "bKash Payment",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD12053),
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    Surface(
                        color = Color(0xFFFCE4EC),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Support Number", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(
                                "01700-000000",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                            Text("Use bKash Send Money / Cash In", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Realistic QR Code pattern
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        QRGrid()
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Scan to Donate", fontWeight = FontWeight.Medium)
                }
            }
            
            Text(
                "Your support helps us build advanced AI features like automatic focus tracking and personalized study insights.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Return to App")
            }
        }
    }
}

@Composable
fun QRGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(12) { rowIndex ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(12) { colIndex ->
                    val isFilled = (rowIndex + colIndex) % 3 == 0 || (rowIndex * colIndex) % 5 == 0 || 
                                   (rowIndex < 3 && colIndex < 3) || (rowIndex > 8 && colIndex < 3) || (rowIndex < 3 && colIndex > 8)
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(if (isFilled) Color.Black else Color.White)
                    )
                }
            }
        }
    }
}
