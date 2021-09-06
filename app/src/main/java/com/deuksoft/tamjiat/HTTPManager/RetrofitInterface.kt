package com.deuksoft.tamjiat.HTTPManager


import com.deuksoft.tamjiat.HTTPManager.DTOManager.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @POST("/auth/verificationUserInfo")
    fun findUser(@Body userInfo : HashMap<String, String>):Call<PublicDTO>

    @Multipart
    @POST("/dash/cropMulter")
    fun sendImage(@Part cropImg : MultipartBody.Part, @PartMap data : HashMap<String, RequestBody>):Call<ResultDTO>
}