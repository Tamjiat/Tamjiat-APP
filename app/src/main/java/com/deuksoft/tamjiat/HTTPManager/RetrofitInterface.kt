package com.deuksoft.tamjiat.HTTPManager


import com.deuksoft.tamjiat.HTTPManager.DTOManager.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface RetrofitInterface {
    @GET("/weather/today")
    fun getTodayWeather(@QueryMap myLocate: HashMap<String, Double>): Call<TodayWeatherDTO>

    @POST("/dash/cropNum")
    fun getTotalNumber(@Body userId: HashMap<String, String>):Call<TotalCropsNumDTO>

    @POST("/dash/cropWeek")
    fun getCropWeek(@Body userId: HashMap<String, String>):Call<List<CropWeekDTO>>

    @POST("/dash/cropDetail")
    fun getCropDetail(@Body userId: HashMap<String, String>):Call<List<CropDetailDTO>>

    @POST("/dash/crop")
    fun getCropSummary(@Body userId: HashMap<String, String>):Call<List<CropSummaryDTO>>

    @POST("/dash/cropCategory")
    fun getCropCategory(@Body userId: HashMap<String, String>):Call<List<CropCategoryDTO>>

    @POST("/dash/cropPercent")
    fun getCropPercent(@Body requestData : HashMap<String, String>):Call<CropPercentDTO>

    @POST("/auth/findUserA")
    fun findUser(@Body userInfo : HashMap<String, String>):Call<PublicDTO>
}