package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropSummaryDTO(
    @SerializedName("category")
    var category : String,

    @SerializedName("count")
    var count : Int
)
