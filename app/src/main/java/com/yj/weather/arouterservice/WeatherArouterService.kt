package com.yj.weather.arouterservice

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.yj.weather.ui.weather.WeatherActivity

class WeatherArouterService private constructor() : IProvider {

    @JvmField
    @Autowired()
    var lng: String? = null

    @JvmField
    @Autowired()
    var lat: String? = null

    @JvmField
    @Autowired()
    var placeName: String? = null

    override fun init(context: Context?) {
        ARouter.getInstance().inject(this)
    }

    fun start(context: Context) {
//        WeatherActivity.start(context,lng,lat,placeName)
    }
}