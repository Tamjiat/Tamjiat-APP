package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName

data class ResultDTO(
    @SerializedName("cdName")
    var cdName: String,

    @SerializedName("cdNameEng")
    var cdNameEng: String,

    @SerializedName("cdSolution")
    var cdSolution: String,

    @SerializedName("cdSickness")
    var cdSickness: String,

    @SerializedName("cdPathogen")
    var cdPathogen: String,

    @SerializedName("cdOccurDate")
    var cdOccurDate: String,
)
