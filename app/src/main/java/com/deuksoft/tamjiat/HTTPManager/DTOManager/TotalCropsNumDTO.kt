package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class TotalCropsNumDTO(
    @SerializedName("totalNum")
    var totalNum : String
)