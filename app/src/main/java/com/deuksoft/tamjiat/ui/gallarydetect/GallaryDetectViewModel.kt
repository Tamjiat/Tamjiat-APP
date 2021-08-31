package com.deuksoft.tamjiat.ui.gallarydetect

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.ImageRepository

class GallaryDetectViewModel(application: Application): AndroidViewModel(application) {
    var imageRepository = ImageRepository()

    fun sendGallayImg(bitmap: Bitmap, userId : String):LiveData<String>{
        return imageRepository.sendImage(bitmap, userId)
    }
}