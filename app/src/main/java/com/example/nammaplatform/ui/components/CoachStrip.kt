package com.example.nammaplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoachStrip(
    coaches: List<String>,
    crowdData: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "ಬೋಗಿ ವಿನ್ಯಾಸ / COACH LAYOUT",
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF0F172A).copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth().height(110.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            itemsIndexed(items = coaches) { index, coach ->
                val isSelected = index == selectedIndex
                val crowdStatus = crowdData.getOrElse(index) { "low" }

                val indicatorColor = when (crowdStatus) {
                    "low" -> Color(0xFF22C55E)
                    "medium" -> Color(0xFFEAB308)
                    "high" -> Color(0xFFEF4444)
                    else -> Color(0xFF22C55E)
                }

                val bgColor = if (coach == "ENG") Color(0xFF27272A) else Color(0xFFEFF6FF)
                val textColor = if (coach == "ENG") Color.White else Color(0xFF0F172A)

                Box(
                    modifier = Modifier
                        .width(105.dp)
                        .fillMaxHeight()
                        .background(color = bgColor, shape = RoundedCornerShape(12.dp))
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) Color(0xFF3B82F6) else indicatorColor.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelect(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // STAR INDICATOR
                        if (coach != "ENG" && crowdStatus == "low") {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⭐", fontSize = 10.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("BEST TO BOARD", fontSize = 7.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF166534))
                            }
                        }

                        Text(text = if (coach == "ENG") "POWER" else "COACH", fontSize = 8.sp, color = textColor.copy(alpha = 0.6f))
                        Text(text = if (coach == "ENG") "ಎಂಜಿನ್" else coach, fontSize = 14.sp, fontWeight = FontWeight.Black, color = textColor)

                        if (coach != "ENG") {
                            Text(
                                text = when(crowdStatus) {
                                    "low" -> "Available"
                                    "medium" -> "Moderate"
                                    "high" -> "Packed"
                                    else -> ""
                                },
                                fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = indicatorColor
                            )
                        }
                    }
                }
            }
        }
    }
}