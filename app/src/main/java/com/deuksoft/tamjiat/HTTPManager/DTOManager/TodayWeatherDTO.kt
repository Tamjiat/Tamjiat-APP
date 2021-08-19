package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class TodayWeatherDTO (
    @SerializedName("message")
    var message : String,
    @SerializedName("result")
    var result : TodayWeatherInfo,
)

data class TodayWeatherInfo(
    @SerializedName("currTemp")
    var currTemp : String,
    @SerializedName("currWeatherIcon")
    var currWeatherIcon : String,
)