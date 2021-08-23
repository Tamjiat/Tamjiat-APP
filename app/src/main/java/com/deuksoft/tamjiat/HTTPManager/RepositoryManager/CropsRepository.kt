package com.deuksoft.tamjiat.HTTPManager.RepositoryManager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deuksoft.tamjiat.HTTPManager.DTOManager.*
import com.deuksoft.tamjiat.HTTPManager.RetrofitAPI
import com.deuksoft.tamjiat.HTTPManager.RetrofitInterface
import com.deuksoft.tamjiat.HTTPManager.Tools
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CropsRepository {
    private val retrofit : Retrofit = RetrofitAPI.getInstance(Tools().MAIN_URL)
    private val service = retrofit.create(RetrofitInterface::class.java)
    val message =  MutableLiveData<String>()

    fun getTotalCropsNum(userId : String): LiveData<String>{
        var userInfo = hashMapOf(
            "uid" to userId
        )
        var result = MutableLiveData<String>()
        service.getTotalNumber(userInfo).enqueue(object :Callback<TotalCropsNumDTO>{
            override fun onResponse(call: Call<TotalCropsNumDTO>, response: Response<TotalCropsNumDTO>) {
               if(response.code() == 200) {
                   result.value = response.body()!!.totalNum
               }
            }
            override fun onFailure(call: Call<TotalCropsNumDTO>, t: Throwable) {
               message.value = "서버와의 통신에 실패했습니다."
            }

        })
        return result
    }

    fun getCropWeek(userId : String): LiveData<List<CropWeekDTO>>{
        var userInfo = hashMapOf(
            "uid" to userId
        )
        val result = MutableLiveData<List<CropWeekDTO>>()
        service.getCropWeek(userInfo).enqueue(object :Callback<List<CropWeekDTO>>{
            override fun onResponse(call: Call<List<CropWeekDTO>>, response: Response<List<CropWeekDTO>>) {
                result.value = response.body()
            }

            override fun onFailure(call: Call<List<CropWeekDTO>>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }
        })
        return result
    }

    fun getCropDetail(userId : String):LiveData<List<CropDetailDTO>>{
        var userInfo = hashMapOf(
            "uid" to userId
        )
        val result = MutableLiveData<List<CropDetailDTO>>()
        service.getCropDetail(userInfo).enqueue(object : Callback<List<CropDetailDTO>>{
            override fun onResponse(call: Call<List<CropDetailDTO>>, response: Response<List<CropDetailDTO>>) {
                if(response.code() == 200){
                    result.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<CropDetailDTO>>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }
        })
        return result
    }

    fun getCropSummary(userId : String):LiveData<List<CropSummaryDTO>>{
        var userInfo = hashMapOf(
            "uid" to userId
        )
        val result = MutableLiveData<List<CropSummaryDTO>>()
        service.getCropSummary(userInfo).enqueue(object : Callback<List<CropSummaryDTO>>{
            override fun onResponse(call: Call<List<CropSummaryDTO>>, response: Response<List<CropSummaryDTO>>) {
                result.value = response.body()
            }

            override fun onFailure(call: Call<List<CropSummaryDTO>>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }
        })
        return result
    }

    fun getCropCategory(userId: String):LiveData<List<CropCategoryDTO>>{
        var userInfo = hashMapOf(
            "uid" to userId
        )
        val result = MutableLiveData<List<CropCategoryDTO>>()
        service.getCropCategory(userInfo).enqueue(object : Callback<List<CropCategoryDTO>>{
            override fun onResponse(call: Call<List<CropCategoryDTO>>, response: Response<List<CropCategoryDTO>>) {
                result.value = response.body()
            }
            override fun onFailure(call: Call<List<CropCategoryDTO>>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }
        })
        return result
    }

    fun getCropPercent(userId: String, cropsName: String, myLocate : HashMap<String, Double>): LiveData<CropPercentDTO>{
        var requestData = hashMapOf(
            "uid" to userId,
            "cropsName" to cropsName,
            "lon" to myLocate["lon"].toString(),
            "lat" to myLocate["lat"].toString()
        )
        val result = MutableLiveData<CropPercentDTO>()
        service.getCropPercent(requestData).enqueue(object : Callback<CropPercentDTO>{
            override fun onResponse(call: Call<CropPercentDTO>, response: Response<CropPercentDTO>) {
                result.value = response.body()
            }

            override fun onFailure(call: Call<CropPercentDTO>, t: Throwable) {
                message.value = "서버와의 통신에 실패하였습니다."
            }
        })
        return result
    }
}