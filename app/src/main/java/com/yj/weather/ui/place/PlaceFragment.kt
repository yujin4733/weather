package com.yj.weather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.yj.weather.Contract
import com.yj.weather.MainActivity
import com.yj.weather.R
import com.yj.weather.util.LogUtil
import com.yj.weather.util.showToast
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
//            val intent = Intent(context, WeatherActivity::class.java).apply {
//                putExtra("locationLng", place.location.lng)
//                putExtra("locationLat", place.location.lat)
//                putExtra("placeName", place.name)
//            }
//            startActivity(intent)
            ARouter.getInstance().build(Contract.WEATHER_ACTIVITY_URL)
                .withString("locationLng",place.location.lng)
                .withString("locationLat",place.location.lat)
                .withString("placeName",place.name)
                .navigation()
            activity?.finish()
            return
        }
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter

        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        btnCurrentPosition.setOnClickListener {
            ARouter.getInstance().build(Contract.WEATHER_ACTIVITY_URL)
                .withString("locationLng","")
                .withString("locationLat","")
                .withString("placeName","当前位置")
                .navigation()
        }

        viewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                "未能查询到任何地点".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}