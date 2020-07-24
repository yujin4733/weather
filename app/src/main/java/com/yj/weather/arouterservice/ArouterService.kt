package com.yj.weather.arouterservice

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface ArouterService: IProvider {

    fun start(context: Context,locationLng: String, locationLat: String,placeName: String )
}