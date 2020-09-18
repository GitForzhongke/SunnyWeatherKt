package com.zhong.sunnyweatherkt.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        val pressure: Float,
        val wind: Wind,
        val precipitation: Precipitation,
        val visibility: Float,
        val humidity: Double,
        @SerializedName("apparent_temperature") val apparentTemperature: Float,
        @SerializedName("life_index") val lifeIndex: LifeIndex,
        @SerializedName("air_quality") val airQuality: AirQuality
    )


    data class LifeIndex(
        val comfort: Comfort,
        val ultraviolet: Ultraviolet
    )

    data class Comfort(
        val index: Int,
        val desc: String
    )

    data class Ultraviolet(
        val index: Int,
        val desc: String
    )

    data class Precipitation(
        val local: Local,
        val nearest: Nearest
    )

    data class Nearest(val status: String, val distance: String, val intensity: Double)
    data class Local(val status: String, val datasource: String, val intensity: Double)

    data class Wind(val direction: Double, val speed: Double)
    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}