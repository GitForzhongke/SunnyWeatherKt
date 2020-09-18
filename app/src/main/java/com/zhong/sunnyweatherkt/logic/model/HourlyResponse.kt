package com.zhong.sunnyweatherkt.logic.model

import com.google.gson.annotations.SerializedName

/**
 *@Time: 2020/9/17
 *@Description:
 *@Author: zhongke
 */
data class HourlyResponse(val status: String, val result: Result) {

    data class Result(val hourly: Hourly)

    data class Hourly(
        val status: String,
        val description: String,//预报自然语言描述
        val precipitation: List<Precipitation>,
        val skycon: List<Skycon>,
        val wind: List<Wind>,
        val humidity: List<Humidity>,
        val cloudrate: List<Cloudrate>,
        val temperature: List<Temperature>,
        val pressure: List<Pressure>,
        val visibility: List<Visibility>,
        val dswrf: List<Dswrf>,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    //短波辐射下向通量
    data class AirQuality(val aqi: List<AQI>, @SerializedName("pm25") val pmNumber: List<PMNumber>)


    data class AQI(val datetime: String, val value: AQIValue)

    data class AQIValue(val chn: Int, val usa: Int)

    data class PMNumber(val datetime: String, val value: Int)

    //短波辐射下向通量
    data class Dswrf(val datetime: String, val value: Double)


    //能见度
    data class Visibility(val datetime: String, val value: Double)

    //气压
    data class Pressure(val datetime: String, val value: String)

    //温度
    data class Temperature(val datetime: String, val value: Double)

    //云量
    data class Cloudrate(val datetime: String, val value: Double)

    //相对湿度
    data class Humidity(val datetime: String, val value: Double)

    //风力，风向
    data class Wind(val datetime: String, val speed: Double, val direction: Double)

    //天气状态
    data class Skycon(val datetime: String, val value: String)

    //降雨
    data class Precipitation(
        val datetime: String,
        val value: Double,
        val speed: Double,
        val direction: Double
    )
}