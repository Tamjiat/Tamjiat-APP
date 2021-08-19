package com.deuksoft.tamjiat.HTTPManager


import com.deuksoft.tamjiat.HTTPManager.DTOManager.TodayWeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitInterface {
    @GET("/weather/today")
    fun getTodayWeather(@QueryMap myLocate: HashMap<String, Double>): Call<TodayWeatherDTO>
}