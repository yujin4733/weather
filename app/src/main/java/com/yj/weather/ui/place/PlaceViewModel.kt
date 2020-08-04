package com.yj.weather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yj.weather.logic.Repository
import com.yj.weather.logic.location.LocationService
import com.yj.weather.logic.model.Place

class PlaceViewModel:ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    private val locationLiveData = MutableLiveData<Place>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){
        query->Repository.searchPlaces(query)
    }



    fun startLocation(){
        LocationService.startLocation()
    }
    fun searchPlaces(query:String){
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}