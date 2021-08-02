package com.deuksoft.tamjiat.activity.login

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginBinding.lifecycleOwner = this
        loginBinding.loginViewModel = loginViewModel

        loginBinding.loginBtn.setOnTouchListener(View.OnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    loginBinding.loginBtn.background = getDrawable(R.drawable.login_btn_back_nagative)
                    loginBinding.loginBtn.setTextColor(Color.BLACK)
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP->{
                    loginBinding.loginBtn.background = getDrawable(R.drawable.login_btn_back)
                    loginBinding.loginBtn.setTextColor(Color.WHITE)
                    return@OnTouchListener true
                }
                else->{
                    return@OnTouchListener false
                }
            }
        })

    }
}