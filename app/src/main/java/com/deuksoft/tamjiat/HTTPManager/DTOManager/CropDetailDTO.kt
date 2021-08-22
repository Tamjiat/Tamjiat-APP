package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class CropDetailDTO(
    @SerializedName("cropsName")
    var cropsName : String,
    @SerializedName("cropsCultivar")
    var cropsCultivar : String,
    @SerializedName("locate")
    var locate : String,
    @SerializedName("cropsStart")
    var cropsStart : String,

)
