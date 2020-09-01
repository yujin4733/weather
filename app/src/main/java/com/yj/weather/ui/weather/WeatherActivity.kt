package com.yj.weather.ui.weather

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.yj.weather.Contract
import com.yj.weather.R
import com.yj.weather.base.BaseActivity
import com.yj.weather.logic.location.LocationService
import com.yj.weather.logic.location.PlaceInterface
import com.yj.weather.logic.model.Place
import com.yj.weather.logic.model.Weather
import com.yj.weather.logic.model.getSky
import com.yj.weather.util.showToast
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

@Route(path = Contract.WEATHER_ACTIVITY_URL)
class WeatherActivity : BaseActivity(), PlaceInterface {


    @Autowired(name = "locationLng")
    lateinit var lng: String

    @Autowired(name = "locationLat")
    lateinit var lat: String

    //    @JvmField
    @Autowired(name = "placeName")
    lateinit var place: String

    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    companion object {
        fun start(context: Context, locationLng: String, locationLat: String, placeName: String) {
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("locationLng", locationLng)
                putExtra("locationLat", locationLat)
                putExtra("placeName", placeName)
            }
            context.startActivity(intent)
        }
    }

    override fun layoutRes() = R.layout.activity_weather

    override fun initDate() {
        ARouter.getInstance().inject(this)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = lng ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = lat ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = place ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                getString(R.string.unable_to_get_weather_info).showToast()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        })
        if (viewModel.placeName.equals("当前位置")) {
            LocationService.startLocation()
        } else {
            refreshWeather()
        }
    }

    override fun initView() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener { refreshWeather() }

        navBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }


        })

        LocationService.setLocationListener(this)
    }


    override fun setPlace(place: Place) {
        viewModel.locationLng = place.location.lng
        viewModel.locationLat = place.location.lat
        viewModel.placeName = place.name
        viewModel.savePlace(place)
        refreshWeather()
    }


    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        swipeRefresh.isRefreshing = true
    }

    /**
     * 展示天气信息
     */
    private fun showWeatherInfo(weather: Weather) {
        placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        // 填充now.xml布局中数据
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationService.stopLocation()
        LocationService.removeListerer()

    }
}
