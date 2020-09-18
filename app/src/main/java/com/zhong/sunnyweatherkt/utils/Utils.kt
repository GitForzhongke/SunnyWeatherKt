package com.zhong.sunnyweatherkt.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

/**
 *@Time: 2020/9/18
 *@Description:
 *@Author: zhongke
 */
object Utils {

    private val listData = arrayOf("", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日")


    fun getWeek(time: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = format.parse(time)
        val dateForWeek: Int
        dateForWeek = if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            7
        } else {
            calendar.get(Calendar.DAY_OF_WEEK) - 1
        }
        return listData[dateForWeek]
    }

    fun getWindOrientation(orientation: Double): String {
        val windOrientation = round(orientation).toInt()
        return when {
            windOrientation < 90 -> "东北"
            windOrientation == 90 -> "东"
            windOrientation in 91..179 -> "东南"
            windOrientation == 180 -> "南"
            windOrientation in 181..269 -> "西南"
            windOrientation == 270 -> "西"
            windOrientation in 271..359 -> "西北"
            else -> "北"
        }
    }
}