package com.deuksoft.tamjiat.HTTPManager.DTOManager

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResultDTO(
    @SerializedName("result")
    var result : ResultDetail
)

data class ResultDetail(
    @SerializedName("cdName")
    var cdName: String,

    @SerializedName("cropsImage")
    var cropsImage: String,

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
):Serializable