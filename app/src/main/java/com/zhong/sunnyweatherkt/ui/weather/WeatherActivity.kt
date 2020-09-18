package com.zhong.sunnyweatherkt.ui.weather

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.zhong.sunnyweatherkt.weatherenum.CollapsingToolbarLayoutState
import com.zhong.sunnyweatherkt.R
import com.zhong.sunnyweatherkt.base.BaseActivity
import com.zhong.sunnyweatherkt.bean.HourlyItemBean
import com.zhong.sunnyweatherkt.logic.model.Weather
import com.zhong.sunnyweatherkt.logic.model.getSky
import com.zhong.sunnyweatherkt.showToast
import com.zhong.sunnyweatherkt.utils.LogUtil
import com.zhong.sunnyweatherkt.utils.Utils
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import kotlinx.android.synthetic.main.weather_info.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.round

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

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hourly_recyclerview.layoutManager = layoutManager
        val hourlyAdapter = HourlyAdapter(mutableListOf())
        hourly_recyclerview.adapter = hourlyAdapter

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
                if (abs(verticalOffset) >= 110) {
                    person_address.visibility = View.VISIBLE
                    address_weather.visibility = View.VISIBLE
                    placeName.visibility = View.GONE
                    currentSky.visibility = View.GONE
                } else {
                    currentSky.visibility = View.VISIBLE
                    placeName.visibility = View.VISIBLE
                    person_address.visibility = View.GONE
                    address_weather.visibility = View.GONE
                }
                val textColor = if (abs(verticalOffset) in 220..250) Color.parseColor("#66FFFFFF")
                else if (abs(verticalOffset) in 180..219) Color.parseColor("#80FFFFFF")
                else if (abs(verticalOffset) in 140..179) Color.parseColor("#99FFFFFF")
                else if (abs(verticalOffset) in 100..139) Color.parseColor("#B3FFFFFF")
                else if (abs(verticalOffset) in 60..99) Color.parseColor("#CCFFFFFF")
                else if (abs(verticalOffset) in 20..59) Color.parseColor("#E6FFFFFF")
                else if (abs(verticalOffset) in 0..19) Color.parseColor("#FFFFFF")
                else Color.parseColor("#00FFFFFF")
                currentTemp.setTextColor(textColor)
                currentAQI.setTextColor(textColor)


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
        val hourlyList = mutableListOf<HourlyItemBean>()

        val temperatureSize = weather.hourly.temperature.size
        for (i in 0 until temperatureSize) {
            val hourlyBean = HourlyItemBean(
                weather.hourly.temperature[i].datetime,
                round(weather.hourly.temperature[i].value).toInt(),
                getSky(weather.hourly.skycon[i].value)
            )
            hourlyList.add(hourlyBean)
        }
        (hourly_recyclerview.adapter as HourlyAdapter).setDates(hourlyList)

        val mInflater = LayoutInflater.from(this)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT+8");

        val realtime = weather.realtime
        val daily = weather.daily


        placeName.text = viewModel.placeName
        val currentTempText = "${realtime.temperature.toInt()}"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        person_address.text = viewModel.placeName
        address_weather.text = getSky(realtime.skycon).info
        drawerLayout.setBackgroundResource(getSky(realtime.skycon).bg)
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

            dateInfo.text = Utils.getWeek(simpleDateFormat.format(skycon.date))
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} °C"

            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }

        val description = "今天:  ${weather.result.hourly.description}"
        weather_description.text = description

        info_AQI.text = realtime.airQuality.aqi.chn.toInt().toString()
        info_apparent_temperature.text =
            round(weather.realtime.apparentTemperature).toInt().toString()
        info_comfort.text = weather.realtime.lifeIndex.comfort.desc
        info_ultraviolet.text = weather.realtime.lifeIndex.ultraviolet.desc
        info_visibility.text = weather.realtime.visibility.toString()
        info_intensity.text = weather.realtime.precipitation.local.intensity.toInt().toString()
        val infoPressureText = "${round(weather.realtime.pressure / 100).toInt()}百帕"
        info_pressure.text = infoPressureText
        val windText =
            "${Utils.getWindOrientation(weather.realtime.wind.direction)} ${weather.realtime.wind.speed}公里/小时"
        info_wind.text = windText
        val humidityText = "${(weather.realtime.humidity * 100).toInt()}%"
        info_humidity.text = humidityText

//        val lifeIndex = daily.lifeIndex
//        coldRiskText.text = lifeIndex.coldRisk[0].desc
//        dressingText.text = lifeIndex.dressing[0].desc
//        ultravioletText.text = lifeIndex.ultraviolet[0].desc
//        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }
}
