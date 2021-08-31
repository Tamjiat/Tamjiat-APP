package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropWeekDTO(
    @SerializedName("cropsName")
    var cropsName: String,
    @SerializedName("month")
    var month : String
)