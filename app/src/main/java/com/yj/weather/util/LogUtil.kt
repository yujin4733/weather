package com.yj.weather.util

import android.util.Log
import com.yj.weather.BuildConfig

object LogUtil {

    private val debug :Boolean = BuildConfig.LOG_DEBUG

    fun d(tag:String,msg:String){
        if(debug){
            Log.d(tag,msg)
        }
    }

    fun w(tag:String,msg:String){
        if(debug){
            Log.w(tag,msg)
        }
    }

    fun e(tag:String,msg:String){
        if(debug){
            Log.e(tag,msg)
        }
    }
}