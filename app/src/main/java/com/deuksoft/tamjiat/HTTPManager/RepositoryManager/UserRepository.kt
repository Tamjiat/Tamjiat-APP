package com.deuksoft.tamjiat.HTTPManager.RepositoryManager

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deuksoft.tamjiat.HTTPManager.DTOManager.PublicDTO
import com.deuksoft.tamjiat.HTTPManager.RetrofitAPI
import com.deuksoft.tamjiat.HTTPManager.RetrofitInterface
import com.deuksoft.tamjiat.HTTPManager.Tools
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository {
    private val retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private val service = retrofit.create(RetrofitInterface::class.java)
    val message =  MutableLiveData<String>()

    fun findUser(context: Context):LiveData<String>{
        var userInfo = UserInfo(context)
        var result = MutableLiveData<String>()
        var userData = hashMapOf(
            "userid" to userInfo.getUserInfo()["USER_ID"].toString(),
            "userName" to userInfo.getUserInfo()["USER_NAME"].toString(),
            "img" to userInfo.getUserInfo()["USER_IMAGE"].toString(),
            "email" to userInfo.getUserInfo()["USER_EMAIL"].toString()
        )
        service.findUser(userData).enqueue(object : Callback<PublicDTO>{
            override fun onResponse(call: Call<PublicDTO>, response: Response<PublicDTO>) {
                result.value = response.body()!!.message
            }

            override fun onFailure(call: Call<PublicDTO>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }

        })
        return result
    }
}
