package com.yj.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App:Application() {
    companion object{

        const val CAIYUN_TOKEN = "AYl2K28IQmNra1E9"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}