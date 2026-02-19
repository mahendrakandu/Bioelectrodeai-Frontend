package com.simats.learning.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simats.learning.models.SignalData
import java.io.IOException

class SignalDatasetReader(private val context: Context) {

    private val gson = Gson()
    private val fileName = "signals_dataset.json"

    data class SignalsWrapper(val signals: List<SignalData>)

    fun getAllSignals(): List<SignalData> {
        val jsonString = getJsonDataFromAsset() ?: return emptyList()
        return try {
            val wrapper = gson.fromJson(jsonString, SignalsWrapper::class.java)
            wrapper.signals
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun getSignalByType(type: String): SignalData? {
        return getAllSignals().find { 
            it.signalType.equals(type, ignoreCase = true) 
        }
    }

    private fun getJsonDataFromAsset(): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}
