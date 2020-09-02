package com.yj.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.yj.weather.base.BaseActivity
import com.yj.weather.util.StatusBarUtil

@Route(path = Contract.MAIN_ACTIVITY)
class MainActivity : BaseActivity() {

    override fun layoutRes() = R.layout.activity_main

    override fun initDate() {

    }

//    override fun setStatusBar() {
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimary), 0)
//    }


}
