package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropCategoryDTO(
    @SerializedName("cropsNum")
    var cropsNum : Int,
    @SerializedName("cropsName")
    var cropsName : String
)
