package com.example.nammaplatform.data

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

data class Station(
    val en: String,
    val kn: String
)

data class Train(
    val id: String,
    val name: String,
    val platform: String,
    val expectedTime: String,
    val stations: List<String>,
    val coaches: List<String>,
    val crowd: List<String>,
    val announcement: String
)

data class TrainData(
    val stations: List<Station>,
    val trains: List<Train>
)

object TrainRepository {
    fun loadData(context: Context): TrainData? {
        val jsonString: String
        try {
            jsonString = context.assets.open("trains.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return Gson().fromJson(jsonString, TrainData::class.java)
    }
}