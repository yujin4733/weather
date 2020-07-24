package com.yj.weather.arouterservice

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.yj.weather.ui.weather.WeatherActivity

@Route(path = "/weather/weatherArouterService")
class WeatherArouterService: ArouterService {

    override fun init(context: Context?) {

    }

    override fun start(context: Context,locationLng: String, locationLat: String,placeName: String) {
        WeatherActivity.start(context,locationLng,locationLat,placeName)
    }


}