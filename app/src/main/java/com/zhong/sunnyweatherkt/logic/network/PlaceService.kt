package com.zhong.sunnyweatherkt.logic.network

import com.zhong.sunnyweatherkt.Config
import com.zhong.sunnyweatherkt.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */
interface PlaceService {

    @GET("v2/place?token=${Config.WEATHER_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}