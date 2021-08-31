package com.deuksoft.tamjiat.activity.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.activity.login.LoginActivity
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kakao.sdk.user.UserApiClient
import java.util.jar.Manifest

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            getPermission()
        }, 1500)

    }

    private fun getPermission(){
        var permission = object : PermissionListener{
            override fun onPermissionGranted() {
                checkLogin()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                finishAffinity()
            }

        }
        TedPermission.with(this)
            .setPermissionListener(permission)
            .setRationaleTitle("권한 요청")
            .setRationaleMessage("앱을 사용하기위해서는 권한 허용이 필요합니다!")
            .setDeniedMessage("승인 거부 [설정] > [권한]에서 권한 승인 가능")
            .setPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()
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