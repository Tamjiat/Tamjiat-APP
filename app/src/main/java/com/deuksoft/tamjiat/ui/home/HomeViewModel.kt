package com.deuksoft.tamjiat.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deuksoft.tamjiat.GISManager.GetMyLocation
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropWeekDTO
import com.deuksoft.tamjiat.HTTPManager.DTOManager.TodayWeatherInfo
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.CropsRepository
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.WeatherRepository
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherRepository = WeatherRepository()
    private val cropsRepository = CropsRepository()

    fun getTodayWeather(myLocation: HashMap<String, Double>):LiveData<TodayWeatherInfo>{
        return weatherRepository.getTodayWeather(myLocation)
    }

    fun getWeatherFailedMessage():LiveData<String>{
        return weatherRepository.message
    }

    fun getTotalCropsNum(userId:String):LiveData<String>{
        return cropsRepository.getTotalCropsNum(userId)
    }

    fun getCropWeek(userId: String):LiveData<List<CropWeekDTO>>{
        return cropsRepository.getCropWeek(userId)
    }
    val userName = MutableLiveData<String>().apply {
        value = UserInfo(application).getUserInfo()["USER_NAME"]
    }

    var userImageUrl = MutableLiveData<String>().apply {
        value = UserInfo(application).getUserInfo()["USER_IMAGE"]
    }
}