package com.deuksoft.tamjiat.HTTPManager.RepositoryManager

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deuksoft.tamjiat.HTTPManager.DTOManager.PublicDTO
import com.deuksoft.tamjiat.HTTPManager.RetrofitAPI
import com.deuksoft.tamjiat.HTTPManager.RetrofitInterface
import com.deuksoft.tamjiat.HTTPManager.Tools
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream

class ImageRepository {
    private val retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private val service = retrofit.create(RetrofitInterface::class.java)
    val message =  MutableLiveData<String>()

    fun sendImage(bitmap: Bitmap, userId :String): LiveData<String> {
        var result = MutableLiveData<String>()

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        var requestBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
        var uploadImg = MultipartBody.Part.createFormData("myFile", "${userId}.jpg", requestBody)
        var id = RequestBody.create(MediaType.parse("text/plain"), userId)

        var info = hashMapOf(
            "userId" to id
        )
        service.sendImage(uploadImg, info).enqueue(object :Callback<PublicDTO>{

            override fun onResponse(call: Call<PublicDTO>, response: Response<PublicDTO>) {
                result.value = response.body()!!.message
            }

            override fun onFailure(call: Call<PublicDTO>, t: Throwable) {
                message.value = "서버와의 통신이 원활하지 않습니다."
            }

        })
        return result
    }

}