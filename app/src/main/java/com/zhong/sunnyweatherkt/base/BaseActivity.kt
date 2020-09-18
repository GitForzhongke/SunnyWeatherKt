package com.zhong.sunnyweatherkt.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */


abstract class BaseActivity : AppCompatActivity() {

    fun getDefTag() = BaseFragment::class.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasFullScreenUI()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val decorView = window.decorView
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = Color.TRANSPARENT
//                window.navigationBarColor = Color.TRANSPARENT
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val layoutParams = window.attributes
            layoutParams.flags =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or layoutParams.flags
        }
        setContentView(getLayoutId())

        initView()
    }

    open fun hasFullScreenUI(): Boolean {
        return false
    }

    abstract fun getLayoutId(): Int


    abstract fun getData()

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()

        getData()
    }
}