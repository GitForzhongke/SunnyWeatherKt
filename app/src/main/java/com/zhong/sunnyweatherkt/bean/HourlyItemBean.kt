package com.zhong.sunnyweatherkt.bean

import com.zhong.sunnyweatherkt.logic.model.Sky

/**
 *@Time: 2020/9/17
 *@Description:
 *@Author: zhongke
 */

data class HourlyItemBean(val dateTime:String,val temperature:Int,val skycon: Sky)