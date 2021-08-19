package com.deuksoft.tamjiat.Database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    @PrimaryKey val kakaoId : String,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "userName") val userName : String,
    @ColumnInfo(name= "userImage") val userImage : String
)
