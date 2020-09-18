package com.zhong.sunnyweatherkt.logic.model

data class Weather(
    val realtime: RealtimeResponse.Realtime,
    val daily: DailyResponse.Daily,
    val hourly: HourlyResponse.Hourly,
    val result: WeatherResponse.Result
)