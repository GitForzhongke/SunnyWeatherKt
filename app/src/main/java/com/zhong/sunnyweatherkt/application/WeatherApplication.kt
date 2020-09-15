package com.zhong.sunnyweatherkt.application

import android.app.Application
import android.content.Context

/**
 *@Time: 2020/9/15
 *@Description:
 *@Author: zhongke
 */

class WeatherApplication : Application() {
    companion object {
        lateinit var weatherContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        weatherContext = applicationContext
    }
}