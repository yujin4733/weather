package com.yj.weather.logic

import androidx.lifecycle.liveData
import com.yj.weather.logic.model.Place
import com.yj.weather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = WeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun serarchP(query: String) = liveData(Dispatchers.IO){
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(query)
            if(placeResponse.status == "ok"){
                val place = placeResponse.places
                Result.success(place)
            }else{
                Result.failure(java.lang.RuntimeException("resopnse status is ${placeResponse.status}"))
            }
        }catch (e:java.lang.Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}