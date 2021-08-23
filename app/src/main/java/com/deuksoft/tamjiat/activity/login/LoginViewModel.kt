package com.deuksoft.tamjiat.activity.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.deuksoft.tamjiat.HTTPManager.RepositoryManager.UserRepository

class LoginViewModel(application: Application):AndroidViewModel(application) {
    var userRepository = UserRepository()

    fun findUser(context: Context):LiveData<String>{
        return userRepository.findUser(context)
    }
}