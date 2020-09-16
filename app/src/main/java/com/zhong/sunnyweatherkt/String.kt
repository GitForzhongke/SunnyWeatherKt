package com.zhong.sunnyweatherkt

import android.widget.Toast
import com.zhong.sunnyweatherkt.application.WeatherApplication

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */

fun String.showToast(time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(WeatherApplication.weatherContext, this, time).show()
}