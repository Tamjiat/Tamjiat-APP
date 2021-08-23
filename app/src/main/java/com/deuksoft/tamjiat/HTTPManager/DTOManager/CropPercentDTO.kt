package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropPercentDTO(
    @SerializedName("cropPercent")
    var cropPercent : CropPercentDetail,

    @SerializedName("weather")
    var weather : WeatherDetail
)

data class CropPercentDetail(
    @SerializedName("userID")
    var userID : String,
    @SerializedName("cropsStart")
    var cropsStart : String,
    @SerializedName("cropsEnd")
    var cropsEnd : String,
    @SerializedName("percent")
    var percent : Int
)

data class WeatherDetail(
    @SerializedName("temp")
    var temp : Int,
    @SerializedName("humidity")
    var humidity : Int,
    @SerializedName("windSpeed")
    var windSpeed : Int
)
