package com.zhong.sunnyweatherkt.ui.place


import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhong.sunnyweatherkt.R
import com.zhong.sunnyweatherkt.base.BaseFragment
import com.zhong.sunnyweatherkt.showToast
import kotlinx.android.synthetic.main.fragment_place.*


class PlaceFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var mPlaceAdapter: PlaceAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_place
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        mPlaceAdapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = mPlaceAdapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                mPlaceAdapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer { result ->
            val place = result.getOrNull()
            if (place != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(place)
            } else {
                "未能查询到任何地点".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }

    override fun getData() {

    }
}