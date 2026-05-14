package com.example.nammaplatform.ui.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nammaplatform.data.*
import java.time.LocalDate
import java.time.LocalTime

class TrainViewModel : ViewModel() {

    private var allData: TrainData? = null

    private val _selectedSource =
        mutableStateOf("Select Source")
    val selectedSource: State<String> =
        _selectedSource

    private val _selectedDestination =
        mutableStateOf("Select Destination")
    val selectedDestination: State<String> =
        _selectedDestination

    // DATE
    private val _selectedDate =
        mutableStateOf(LocalDate.now())
    val selectedDate: State<LocalDate> =
        _selectedDate

    private val _upcomingTrains =
        mutableStateOf<List<Train>>(emptyList())
    val upcomingTrains: State<List<Train>> =
        _upcomingTrains

    private val _expandedTrainId =
        mutableStateOf<String?>(null)
    val expandedTrainId: State<String?> =
        _expandedTrainId

    fun init(data: TrainData) {

        allData = data

        if (data.stations.isNotEmpty()) {

            _selectedSource.value =
                data.stations[0].en

            if (data.stations.size > 1) {

                _selectedDestination.value =
                    data.stations[1].en
            }

            updateUpcomingTrains()
        }
    }

    fun setSource(station: String) {

        _selectedSource.value = station
        updateUpcomingTrains()
    }

    fun setDestination(station: String) {

        _selectedDestination.value = station
        updateUpcomingTrains()
    }

    fun setDate(date: LocalDate) {

        _selectedDate.value = date
        updateUpcomingTrains()
    }

    fun setExpanded(trainId: String?) {

        _expandedTrainId.value =
            if (_expandedTrainId.value == trainId)
                null
            else
                trainId
    }

    private fun updateUpcomingTrains() {

        val data = allData ?: return

        val currentTime = LocalTime.now()

        val filtered = data.trains.filter { train ->

            val sourceIndex =
                train.stations.indexOf(
                    _selectedSource.value
                )

            val destinationIndex =
                train.stations.indexOf(
                    _selectedDestination.value
                )

            val trainTime =
                LocalTime.parse(train.expectedTime)

            val isToday =
                _selectedDate.value == LocalDate.now()

            val validTime =
                if (isToday)
                    trainTime.isAfter(currentTime)
                else
                    true

            sourceIndex != -1 &&
                    destinationIndex != -1 &&
                    sourceIndex < destinationIndex &&
                    validTime
        }

        // SHOW ONLY NEXT 3 TRAINS
        _upcomingTrains.value =
            filtered
                .sortedBy { it.expectedTime }
                .take(3)

        if (_upcomingTrains.value.isNotEmpty()) {

            _expandedTrainId.value =
                _upcomingTrains.value[0].id
        }
    }
}