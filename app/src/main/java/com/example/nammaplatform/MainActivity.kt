package com.example.nammaplatform

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nammaplatform.data.TrainRepository
import com.example.nammaplatform.ui.components.TrainCard
import com.example.nammaplatform.ui.components.TrainViewModel
import com.example.nammaplatform.utils.KannadaSpeaker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private val viewModel: TrainViewModel by viewModels()

    private lateinit var speaker: KannadaSpeaker

    private var stationList: List<Pair<String, String>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        speaker = KannadaSpeaker(this)

        val data = TrainRepository.loadData(this)

        data?.let {

            stationList =
                it.stations.map { s -> Pair(s.en, s.kn) }

            viewModel.init(it)
        }

        setContent {

            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF0F172A)
                ) {

                    MainScreen(
                        viewModel = viewModel,
                        speaker = speaker,
                        stations = stationList
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        speaker.shutdown()
        super.onDestroy()
    }
}

@Composable
fun MainScreen(
    viewModel: TrainViewModel,
    speaker: KannadaSpeaker,
    stations: List<Pair<String, String>>
) {

    val context = LocalContext.current

    val selectedSource by viewModel.selectedSource
    val selectedDestination by viewModel.selectedDestination
    val selectedDate by viewModel.selectedDate

    val upcomingTrains by viewModel.upcomingTrains
    val expandedTrainId by viewModel.expandedTrainId

    var showStationDialog by remember {
        mutableStateOf(false)
    }

    var selectingSource by remember {
        mutableStateOf(true)
    }

    val sourceKannada =
        stations.find { it.first == selectedSource }?.second ?: ""

    val destinationKannada =
        stations.find { it.first == selectedDestination }?.second ?: ""

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 200.dp,
            start = 24.dp,
            end = 24.dp
        )
    ) {

        item {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Column {

                    Text(
                        text = "ನಿಮ್ಮ ಪ್ರಯಾಣ / YOUR JOURNEY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // DATE
                    Text(
                        text = "DATE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.clickable {

                            val calendar = Calendar.getInstance()

                            val datePickerDialog = DatePickerDialog(
                                context,

                                { _: DatePicker, year: Int, month: Int, day: Int ->

                                    val pickedDate =
                                        LocalDate.of(
                                            year,
                                            month + 1,
                                            day
                                        )

                                    viewModel.setDate(pickedDate)
                                },

                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )

                            datePickerDialog.show()
                        },

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = selectedDate.format(
                                DateTimeFormatter.ofPattern("dd MMM yyyy")
                            ),

                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFDE047)
                        )

                        Text(
                            text = " 📅",
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // FROM
                    Text(
                        text = "FROM",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.clickable {
                            selectingSource = true
                            showStationDialog = true
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text = selectedSource,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )

                            Text(
                                text = sourceKannada,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE047)
                            )
                        }

                        Text(
                            text = " ▾",
                            fontSize = 22.sp,
                            color = Color(0xFFFDE047)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // TO
                    Text(
                        text = "TO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White.copy(alpha = 0.5f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.clickable {
                            selectingSource = false
                            showStationDialog = true
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text = selectedDestination,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )

                            Text(
                                text = destinationKannada,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFDE047)
                            )
                        }

                        Text(
                            text = " ▾",
                            fontSize = 22.sp,
                            color = Color(0xFFFDE047)
                        )
                    }
                }

                Surface(
                    color = Color(0xFFFDE047),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "HELP",
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        ),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A)
                    )
                }
            }
        }

        items(
            items = upcomingTrains,
            key = { it.id }
        ) { train ->

            TrainCard(
                train = train,
                isExpanded = train.id == expandedTrainId,
                onClick = {
                    viewModel.setExpanded(train.id)
                },
                onSpeak = {
                    speaker.speak(
                        train.name,
                        train.platform
                    )
                }
            )
        }
    }

    // STATION DIALOG
    if (showStationDialog) {

        AlertDialog(
            onDismissRequest = {
                showStationDialog = false
            },

            title = {
                Text("Select Station")
            },

            text = {

                LazyColumn(
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {

                    items(stations) { station ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    if (selectingSource) {
                                        viewModel.setSource(station.first)
                                    } else {
                                        viewModel.setDestination(station.first)
                                    }

                                    showStationDialog = false
                                }
                                .padding(vertical = 12.dp)
                        ) {

                            Text(
                                text = station.first,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = station.second,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showStationDialog = false
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}