package com.yj.weather.logic.network

import com.yj.weather.App
import com.yj.weather.Contract
import com.yj.weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${Contract.CAIYUN_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}