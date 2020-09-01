package com.yj.weather.logic.location

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.yj.weather.App
import com.yj.weather.logic.model.Location
import com.yj.weather.logic.model.Place
import com.yj.weather.util.showToast

object LocationService {

    lateinit var locationClient: AMapLocationClient

    /**
     * 初始化定位
     */
    fun startLocation() {
        //初始化client
        locationClient = AMapLocationClient(App.context)
        val locationOption = getDefaultOption()
        //设置定位参数
        locationClient.setLocationOption(locationOption)
        // 设置定位监听
        locationClient.setLocationListener(locationListener)
        // 启动定位
        locationClient.startLocation()
    }

    fun stopLocation() {
        locationClient.stopLocation()
    }


    /**
     * 默认的定位参数
     */
    private fun getDefaultOption(): AMapLocationClientOption {
        val mOption = AMapLocationClientOption()
        mOption.locationMode =
            AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.interval = 200000//可选，设置定位间隔。默认为2秒
        mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        mOption.isOnceLocation = false//可选，设置是否单次定位。默认是false
        mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        mOption.isWifiScan =
            true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
        mOption.geoLanguage =
            AMapLocationClientOption.GeoLanguage.DEFAULT//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption
    }


    /**
     * 定位监听
     */
    var locationListener: AMapLocationListener =
        AMapLocationListener { location ->
            if (location?.errorCode == 0) {
                //errCode等于0代表定位成功，其他的为定位失败
                val place = Place(
                    location.getDistrict(),
                    Location(location.longitude.toString(), location.latitude.toString()),
                    location.address
                )
                listener?.setPlace(place)
            }else{
                location?.errorInfo?.showToast()
            }
        }

    var listener: PlaceInterface? = null

    fun setLocationListener(listener: PlaceInterface) {
        this.listener = listener
    }

    fun removeListerer() {
        this.listener = null
    }


}