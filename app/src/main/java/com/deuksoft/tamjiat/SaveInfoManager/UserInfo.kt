package com.deuksoft.tamjiat.SaveInfoManager

import android.content.Context
import com.kakao.sdk.user.model.User
/*
* @author: 김득회
* @작성일 : 2021-08-22
* */
class UserInfo(context: Context) {
    var userInfoData = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
    var editor = userInfoData.edit()

    /*
    * 사용자 정보를 저장하고 읽고 삭제하는 곳이다. 사용자가 로그인을 하면 정보를 저장하고, 로그아웃을 하게 되면 제거를 한다.
    * */
    fun setUserInfo(user : User){
        editor.apply {
            putString(USER_ID, user.id.toString()).apply()
            putString(USER_EMAIL, user.kakaoAccount?.email).apply()
            putString(USER_NAME, user.kakaoAccount?.profile?.nickname).apply()
            putString(USER_IMAGE, user.kakaoAccount?.profile?.profileImageUrl).apply()
        }
    }

    fun getUserInfo():HashMap<String, String>{
        return hashMapOf(
            USER_ID to userInfoData.getString(USER_ID, "")!!.toString(),
            USER_EMAIL to userInfoData.getString(USER_EMAIL, "")!!.toString(),
            USER_NAME to userInfoData.getString(USER_NAME, "")!!.toString(),
            USER_IMAGE to userInfoData.getString(USER_IMAGE, "")!!.toString(),
        )
    }

    fun removeUserInfo(){
        editor.clear().apply()
    }

    companion object{
        const val USER_ID = "USER_ID"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_NAME = "USER_NAME"
        const val USER_IMAGE = "USER_IMAGE"
    }
}