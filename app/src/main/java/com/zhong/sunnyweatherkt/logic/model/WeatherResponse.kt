package com.zhong.sunnyweatherkt.logic.model

import com.google.gson.annotations.SerializedName

/**
 *@Time: 2020/9/18
 *@Description:
 *@Author: zhongke
 */

data class WeatherResponse(val status: String, val result: Result) {
    data class Result(
        val realtime: RealTime,
        val hourly: Hourly,
        @SerializedName("forecast_keypoint") val forecastKeypoint: String
    )

    data class Content(
        val status: String,
        @SerializedName("request_status") val requestStatus: String,
        val alertId: String,
        val source: String,
        val title: String,
        val description: String,
        val county: String,
        val city: String,
        val location: String,
        val province: String,
        val adcode: String,
        val regionId: String
    )

    data class RealTime(
        val status: String,
        val temperature: Double,
        val humidity: Double,
        val cloudrate: Double,
        val skycon: String,
        val visibility: Double,
        val dswrf: Double,
        val wind: Wind,
        val pressure: Double,
        @SerializedName("apparent_temperature") val apparentTemperature: Double,
        val precipitation: Precipitation
    )

    data class Wind(val speed: Double, val direction: Double)
    data class Precipitation(
        val local: Local,
        val nearest: Nearest
    )

    data class Nearest(val status: String, val distance: String, val intensity: Double)
    data class Local(val status: String, val datasource: String, val intensity: Double)

    data class Hourly(val description: String)

}