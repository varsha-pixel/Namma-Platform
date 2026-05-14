package com.example.nammaplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammaplatform.data.Train
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TrainCard(
    train: Train,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onSpeak: () -> Unit
) {

    val bgColor =
        if (isExpanded) Color.White
        else Color(0xFF1E293B)

    val textColor =
        if (isExpanded) Color(0xFF0F172A)
        else Color.White

    var selectedCoachIndex by remember(train.id) {
        mutableStateOf(1)
    }

    // DATE
    val currentDate = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = bgColor
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 12.dp else 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text =
                            if (isExpanded)
                                "ಪ್ರಸ್ತುತ ರೈಲು / ACTIVE"
                            else
                                "ಮುಂದಿನ ರೈಲು / NEXT",

                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = textColor.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // TRAIN NAME
                    Text(
                        text = train.name,

                        fontSize =
                            if (isExpanded) 28.sp
                            else 22.sp,

                        fontWeight = FontWeight.Black,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // TRAIN NUMBER
                    Text(
                        text = "Train No: ${train.id}",

                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,

                        color =
                            if (isExpanded)
                                Color(0xFF2563EB)
                            else
                                Color(0xFFFDE047)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // TIME
                    Text(
                        text = train.expectedTime,

                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,

                        color =
                            if (isExpanded)
                                Color.Black
                            else
                                Color(0xFFFDE047)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // DATE
                    Text(
                        text = currentDate,

                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,

                        color = Color.Gray
                    )
                }

                // PLATFORM BOX
                Box(
                    modifier = Modifier
                        .background(
                            color =
                                if (isExpanded)
                                    Color(0xFFFDE047)
                                else
                                    Color.White.copy(alpha = 0.1f),

                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        )
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "PLATFORM",

                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,

                            color =
                                if (isExpanded)
                                    Color.Black
                                else
                                    Color.White.copy(alpha = 0.7f)
                        )

                        Text(
                            text = train.platform,

                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,

                            color =
                                if (isExpanded)
                                    Color.Black
                                else
                                    Color.White
                        )
                    }
                }
            }

            if (isExpanded) {

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF0F172A).copy(alpha = 0.1f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // AUDIO BUTTON
                Button(
                    onClick = { onSpeak() },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F172A)
                    ),

                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text(
                        text = "🔊 ಕನ್ನಡ ಪ್ರಕಟಣೆ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                CoachStrip(
                    coaches = train.coaches,
                    crowdData = train.crowd,
                    selectedIndex = selectedCoachIndex,
                    onSelect = {
                        selectedCoachIndex = it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PlatformGuidanceView(
                    coachIndex = selectedCoachIndex,
                    totalCoaches = train.coaches.size,
                    coachType = train.coaches.getOrElse(selectedCoachIndex) {
                        "GEN"
                    }
                )
            }
        }
    }
}