package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropWeekDTO(
    @SerializedName("cropName")
    var cropName: String,
    @SerializedName("month")
    var month : String
)