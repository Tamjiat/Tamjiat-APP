package com.deuksoft.tamjiat.HTTPManager

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitAPI {
    private var retrofit : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstance(url : String): Retrofit {
        retrofit = null
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(SSLBuilder.getUnsafeOkHttpClient().build()) // ssl 우회
                .build()
        }

        return retrofit!!
    }
}