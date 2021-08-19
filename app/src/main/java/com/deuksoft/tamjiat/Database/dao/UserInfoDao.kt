package com.deuksoft.tamjiat.Database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.deuksoft.tamjiat.Database.model.UserInfo

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM UserInfo")
    fun getUserInfo(): UserInfo

    @Insert
    fun insertUserInfo(userInfo: UserInfo)

    @Query("DELETE FROM USERINFO")
    fun deleteUserInfo()
}