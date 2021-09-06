package com.deuksoft.tamjiat.HTTPManager.RepositoryManager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deuksoft.tamjiat.HTTPManager.DTOManager.TodayWeatherDTO
import com.deuksoft.tamjiat.HTTPManager.DTOManager.TodayWeatherInfo
import com.deuksoft.tamjiat.HTTPManager.RetrofitAPI
import com.deuksoft.tamjiat.HTTPManager.RetrofitInterface
import com.deuksoft.tamjiat.HTTPManager.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class WeatherRepository {
    private val retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private val service = retrofit.create(RetrofitInterface::class.java)
    val message =  MutableLiveData<String>()

    fun getTodayWeather(myLocate : HashMap<String, Double>): LiveData<TodayWeatherInfo>{
        var result = MutableLiveData<TodayWeatherInfo>()

        service.getTodayWeather(myLocate).enqueue(object : Callback<TodayWeatherDTO>{
            override fun onResponse(call: Call<TodayWeatherDTO>, response: Response<TodayWeatherDTO>) {
                if(response.code() == 200){
                    result.value = response.body()!!.result
                }else{
                    message.value = response.body()!!.message
                }
            }
            override fun onFailure(call: Call<TodayWeatherDTO>, t: Throwable) {
               message.value = "서버와의 연결이 원활하지 않습니다."
                Log.e("dsfds", t.message.toString())
            }

        })
        return result
    }
}