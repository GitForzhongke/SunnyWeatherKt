package com.zhong.sunnyweatherkt.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhong.sunnyweatherkt.R
import com.zhong.sunnyweatherkt.bean.HourlyItemBean

/**
 *@Time: 2020/9/17
 *@Description:
 *@Author: zhongke
 */
class HourlyAdapter(private var dateList: List<HourlyItemBean>) :
    RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weatherType: TextView = view.findViewById(R.id.weather_type)
        val weatherTime: TextView = view.findViewById(R.id.weather_time)
        val weatherIcon: ImageView = view.findViewById(R.id.weather_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dateList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hourlyItem = dateList[position]
        val dateTimeReplace = hourlyItem.dateTime.substring(11, 16)
        if (position == 0)
            holder.weatherTime.text = "现在"
        else
            holder.weatherTime.text = dateTimeReplace

        holder.weatherType.text = hourlyItem.temperature.toString()

        holder.weatherIcon.setImageResource(hourlyItem.skycon.icon)
    }

    fun setDates(dates: List<HourlyItemBean>) {
        dateList = dates
        notifyDataSetChanged()
    }
}