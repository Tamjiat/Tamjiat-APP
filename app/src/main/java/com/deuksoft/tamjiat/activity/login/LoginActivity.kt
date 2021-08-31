package com.deuksoft.tamjiat.activity.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    //lateinit var userData: UserInfoData
    var callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        Log.e("error?","sf")
        if(error != null){
            when{
                error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러"+error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (token != null) {
            UserApiClient.instance.me { user, error ->
                Log.e("dsfsd","sdfds")
                if (error != null) {
                    Log.e("Request Fail", "사용자 정보 요청 실패", error)
                }
                else if (user != null) {

                    /*userData.setUserID(user.id.toString())
                    userData.setGender(user.kakaoAccount?.gender.toString())
                    userData.setEmail(user.kakaoAccount?.email.toString())
                    userData.setProfileImg(user.kakaoAccount?.profile!!.profileImageUrl)

                    Log.e("sdfds", userData.getUserData().get("USER_ID").toString())*/
                    Log.e("userProfile", user.kakaoAccount.toString())
                    Log.i("Request Success", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n성별: ${user.kakaoAccount?.gender}")

                    UserInfo(this).setUserInfo(user)//사용자 정보 저장
                    loginViewModel.findUser(this).observe(this){
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }

                }
            }
        }
    }

    lateinit var loginBinding: ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginBinding.lifecycleOwner = this
        loginBinding.loginViewModel = loginViewModel

        Log.d("KeyHash", Utility.getKeyHash(this))

        loginBinding.loginBtn.setOnTouchListener(View.OnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    loginBinding.loginBtn.background = getDrawable(R.drawable.login_btn_back_nagative)
                    loginBinding.loginBtn.setTextColor(Color.BLACK)
                    UserApiClient.instance.run {
                        if(isKakaoTalkLoginAvailable(this@LoginActivity)){
                            loginWithKakaoTalk(this@LoginActivity, callback = callback)
                        }else{
                            Log.e("hello", "world")
                            loginWithKakaoAccount(this@LoginActivity, callback = callback)
                        }
                    }
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