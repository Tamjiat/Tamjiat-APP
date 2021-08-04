package com.deuksoft.tamjiat.kakaoLogin

import android.app.Application
import com.deuksoft.tamjiat.R
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_app_key))
    }
}