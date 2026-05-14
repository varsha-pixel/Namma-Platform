package com.example.nammaplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlatformGuidanceView(
    coachIndex: Int,
    totalCoaches: Int,
    coachType: String
) {
    val relativePos = if (totalCoaches > 1) coachIndex.toFloat() / (totalCoaches - 1).toFloat() else 0.5f
    val distance = coachIndex * 15

    val (zoneName, zoneKN) = when {
        relativePos < 0.33f -> "Left Zone" to "ಎಡ ಭಾಗ"
        relativePos > 0.66f -> "Right Zone" to "ಬಲ ಭಾಗ"
        else -> "Center Zone" to "ಮಧ್ಯ ಭಾಗ"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A).copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text("Platform Zone / ಪ್ಲಾಟ್‌ಫಾರ್ಮ್ ವಲಯ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color.Gray)
        Spacer(modifier = Modifier.height(12.dp))

        // Color Bar
        Row(modifier = Modifier.fillMaxWidth().height(6.dp).background(Color.LightGray.copy(0.3f), RoundedCornerShape(3.dp))) {
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFF3B82F6)))
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEAB308)))
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF4444)))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // RESTORED INDICATORS
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            ZoneIndicator("Left", "🔵", zoneName == "Left Zone")
            ZoneIndicator("Center", "🟡", zoneName == "Center Zone")
            ZoneIndicator("Right", "🔴", zoneName == "Right Zone")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Final Guidance Text
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(12.dp)).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(32.dp).background(Color(0xFF0F172A), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Text("→", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("$coachType Coach Position", fontSize = 11.sp, fontWeight = FontWeight.Black)
                Text("$coachType ಬೋಗಿ: $zoneKN (ಸುಮಾರು $distance ಮೀಟರ್)", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ZoneIndicator(label: String, icon: String, isActive: Boolean) {
    Column(modifier = Modifier.alpha(if (isActive) 1f else 0.2f), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 14.sp)
        Text(label, fontSize = 8.sp, fontWeight = FontWeight.Black)
    }
}