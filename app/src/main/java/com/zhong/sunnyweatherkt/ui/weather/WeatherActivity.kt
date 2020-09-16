package com.zhong.sunnyweatherkt.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.zhong.sunnyweatherkt.weatherenum.CollapsingToolbarLayoutState
import com.zhong.sunnyweatherkt.R
import com.zhong.sunnyweatherkt.base.BaseActivity
import com.zhong.sunnyweatherkt.logic.model.Weather
import com.zhong.sunnyweatherkt.logic.model.getSky
import com.zhong.sunnyweatherkt.showToast
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 *@Time: 2020/9/16
 *@Description:
 *@Author: zhongke
 */

class WeatherActivity : BaseActivity() {


    private var appBarState: CollapsingToolbarLayoutState = CollapsingToolbarLayoutState.EXPANDED

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }


    override fun getLayoutId() = R.layout.activity_weather

    override fun getData() {
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                "无法成功获取天气".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        refreshWeather()
    }

    override fun hasFullScreenUI(): Boolean {
        return true
    }

    override fun initView() {

//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
//
//        swipeRefresh.setOnRefreshListener {
//            refreshWeather()
//        }
//        navBtn.setOnClickListener {
//            drawerLayout.openDrawer(GravityCompat.START)
//        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
        appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (appBarState != CollapsingToolbarLayoutState.EXPANDED) {
                    appBarState = CollapsingToolbarLayoutState.EXPANDED
                }
            } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                if (appBarState != CollapsingToolbarLayoutState.COLLAPSED) {
                    appBarState = CollapsingToolbarLayoutState.COLLAPSED
                }
            } else {
                if (appBarState != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    appBarState = CollapsingToolbarLayoutState.INTERNEDIATE
                }
            }
        })
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }


    private fun showWeatherInfo(weather: Weather) {
        val mInflater = LayoutInflater.from(this)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val realtime = weather.realtime
        val daily = weather.daily
//        placeName.text = viewModel.placeName
//        val currentTempText = "${realtime.temperature.toInt()} °C"
//        currentTemp.text = currentTempText
//        currentSky.text = getSky(realtime.skycon).info
//        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
//        currentAQI.text = currentPM25Text
//        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        drawerLayout.setBackgroundResource(getSky(realtime.skycon).bg)
//        weather_bg.setImageResource(getSky(realtime.skycon).bg)
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = mInflater.inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} °C"

            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}