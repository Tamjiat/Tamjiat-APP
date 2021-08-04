package com.deuksoft.tamjiat.activity.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.activity.login.LoginActivity
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.kakao.sdk.user.UserApiClient

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            checkLogin()
        }, 1500)

    }

    fun checkLogin(){
        UserApiClient.instance.accessTokenInfo{tokenInfo, error ->
            if(tokenInfo != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}