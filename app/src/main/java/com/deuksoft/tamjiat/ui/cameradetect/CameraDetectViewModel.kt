package com.deuksoft.tamjiat.ui.cameradetect

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deuksoft.tamjiat.HTTPManager.DTOManager.ResultDTO
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.ImageRepository
import com.deuksoft.tamjiat.databinding.FragmentGallarydetectBinding
import com.deuksoft.tamjiat.ui.gallarydetect.GallaryDetectViewModel

class CameraDetectViewModel : ViewModel() {

    var imageRepository = ImageRepository()

    fun sendImage(bitmap: Bitmap, userId : String, cropsName : String, beedName : String):LiveData<ResultDTO>{
        return imageRepository.sendImage(bitmap, userId, cropsName, beedName)
    }
}