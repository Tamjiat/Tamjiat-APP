package com.deuksoft.tamjiat.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deuksoft.tamjiat.GISManager.GetMyLocation
import com.deuksoft.tamjiat.HTTPManager.DTOManager.TodayWeatherInfo
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.WeatherRepository
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherRepository = WeatherRepository()

    fun getTodayWeather(myLocation: HashMap<String, Double>):LiveData<TodayWeatherInfo>{
        return weatherRepository.getTodayWeather(myLocation)
    }

    fun getWeatherFailedMessage():LiveData<String>{
        return weatherRepository.message
    }

    val userName = MutableLiveData<String>().apply {
        value = "${UserInfo(application).getUserInfo()["USER_NAME"]} 님!"
    }
}