package com.zhong.sunnyweatherkt.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */


abstract class BaseActivity : AppCompatActivity() {
    protected val TAG: String? = BaseFragment::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        setContentView(getLayoutId())

        initView()
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