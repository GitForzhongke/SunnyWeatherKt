package com.zhong.sunnyweatherkt.logic.network

import com.zhong.sunnyweatherkt.Config
import com.zhong.sunnyweatherkt.logic.model.DailyResponse
import com.zhong.sunnyweatherkt.logic.model.HourlyResponse
import com.zhong.sunnyweatherkt.logic.model.RealtimeResponse
import com.zhong.sunnyweatherkt.logic.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */
interface WeatherService {


    @GET("v2.5/${Config.WEATHER_TOKEN}/{lng},{lat}/weather.json")
    fun getNomalWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<WeatherResponse>

    @GET("v2.5/${Config.WEATHER_TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeResponse>

    @GET("v2.5/${Config.WEATHER_TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<DailyResponse>

    @GET("v2.5/${Config.WEATHER_TOKEN}/{lng},{lat}/hourly.json")
    fun getHourlyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<HourlyResponse>

}