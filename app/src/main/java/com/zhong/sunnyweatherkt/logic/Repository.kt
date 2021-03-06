package com.zhong.sunnyweatherkt.logic

import androidx.lifecycle.liveData
import com.zhong.sunnyweatherkt.logic.dao.PlaceDao
import com.zhong.sunnyweatherkt.logic.model.Place
import com.zhong.sunnyweatherkt.logic.model.Weather
import com.zhong.sunnyweatherkt.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.math.ln

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String, placeName: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng, lat)
            }
            val deferredHourly = async {
                SunnyWeatherNetWork.getHourlyWeather(lng, lat)
            }
            val deferredWeather = async {
                SunnyWeatherNetWork.getNomalWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            val hourlyResponse = deferredHourly.await()
            val weatherResponse = deferredWeather.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok" && hourlyResponse.status == "ok" && weatherResponse.status == "ok") {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    dailyResponse.result.daily,
                    hourlyResponse.result.hourly,
                    weatherResponse.result
                )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}" +
                                "hourly response status is ${hourlyResponse.status}" +
                                "weather response status is ${weatherResponse.status}"
                    )
                )
            }
        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}