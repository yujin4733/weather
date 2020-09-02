package com.yj.weather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yj.weather.R
import com.yj.weather.util.StatusBarUtil

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        setContentView(layoutRes())
        setStatusBar()
        initView()
        initDate()
    }
    protected open fun beforeSetContentView() {

    }
    protected open fun setStatusBar() {
        StatusBarUtil.setColor(this,ContextCompat.getColor(this,R.color.colorPrimary),0)
    }
    abstract fun initDate()

    protected open fun layoutRes() = 0

    protected open fun initView(){

    }

}
