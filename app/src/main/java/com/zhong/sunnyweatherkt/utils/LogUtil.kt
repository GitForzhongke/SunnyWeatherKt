package com.zhong.sunnyweatherkt.utils

import android.util.Log


/**
 *@Time: 2020/9/15
 *@Description:
 *@Author: zhongke
 */
object LogUtil {
    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    private val level = VERBOSE


    fun v(tag: String?, message: String) {
        if (level <= VERBOSE) {
            Log.v(tag, message)
        }
    }

    fun d(tag: String?, message: String) {
        if (level <= DEBUG) {
            Log.d(tag, message)
        }
    }

    fun i(tag: String?, message: String) {
        if (level <= INFO) {
            Log.i(tag, message)
        }
    }

    fun w(tag: String?, message: String) {
        if (level <= WARN) {
            Log.w(tag, message)
        }
    }

    fun e(tag: String?, message: String) {
        if (level <= ERROR) {
            Log.e(tag, message)
        }
    }
}