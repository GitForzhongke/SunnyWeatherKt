package com.zhong.sunnyweatherkt.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.zhong.sunnyweatherkt.application.WeatherApplication
import com.zhong.sunnyweatherkt.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place) {
        getSharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = getSharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = getSharedPreferences().contains("place")

    private fun getSharedPreferences() =
        WeatherApplication.weatherContext.getSharedPreferences(
            "sunny_weather",
            Context.MODE_PRIVATE
        )
}