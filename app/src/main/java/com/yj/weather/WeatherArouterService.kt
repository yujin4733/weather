package com.yj.weather

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.yj.weather.arouterservice.ArouterService
import com.yj.weather.ui.weather.WeatherActivity

@Route(path = Contract.WEATHER_AROUTER_SERVICE)
class WeatherArouterService: ArouterService {

    override fun init(context: Context?) {

    }

    override fun start(context: Context,locationLng: String, locationLat: String,placeName: String) {
        WeatherActivity.start(context,locationLng,locationLat,placeName)
    }


}