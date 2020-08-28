package com.yj.weather.arouterservice

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.yj.weather.Contract
import com.yj.weather.ui.weather.WeatherActivity

class WeatherArouterServiceWarp  private constructor() {

    @Autowired(name = Contract.WEATHER_AROUTER_SERVICE)
    lateinit var service: ArouterService


    init {
        ARouter.getInstance().inject(this)
    }

    companion object {

        val instance = SingletonHolder.holder

        object SingletonHolder {
            val holder = WeatherArouterServiceWarp()
        }
    }


     fun start(context: Context,locationLng: String, locationLat: String,placeName: String) {
         service.start(context,locationLng,locationLat,placeName)
    }

}