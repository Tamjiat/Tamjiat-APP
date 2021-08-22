package com.deuksoft.tamjiat.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropDetailDTO
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropSummaryDTO
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.CropsRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    var cropRepository = CropsRepository()

    fun getCropDetail(userId: String):LiveData<List<CropDetailDTO>>{
        return cropRepository.getCropDetail(userId)
    }

    fun getCropSummary(userId: String):LiveData<List<CropSummaryDTO>>{
        return cropRepository.getCropSummary(userId)
    }

}