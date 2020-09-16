package com.zhong.sunnyweatherkt.logic.network

import com.zhong.sunnyweatherkt.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */
object ServiceCreator {


    private val retrofit = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}