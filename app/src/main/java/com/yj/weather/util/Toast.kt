package com.yj.weather.util

import android.content.Context
import android.widget.Toast
import com.yj.weather.App

fun String.showToast(context:Context  = App.context,duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(App.context,this,duration).show()
}

fun Int.showToast(context: Context = App.context, duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(context,this,duration).show()
}