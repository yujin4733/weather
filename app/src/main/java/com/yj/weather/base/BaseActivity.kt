package com.yj.weather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutRes())
        initView()
        initDate()
    }

    open fun layoutRes() = 0

    open protected fun initView(){

    }

    abstract fun initDate()

}
