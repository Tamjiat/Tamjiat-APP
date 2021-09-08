package com.deuksoft.tamjiat.activity.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.deuksoft.tamjiat.HTTPManager.DTOManager.ResultDetail
import com.deuksoft.tamjiat.HTTPManager.Tools
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.ActivityMainBinding
import com.deuksoft.tamjiat.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var resultBinding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)
        var result = intent.getSerializableExtra("result") as ResultDetail
        Log.e("dsfds", result.toString())
        resultBinding.apply {
            diseaseNameKor.text = result.cdName
            diseaseNameEng.text = result.cdNameEng
            germsNameTxt.text = result.cdPathogen
            calandarDateTxt.text = result.cdOccurDate
            sicknessTxt.text = result.cdSickness
            solutionTxt.text = result.cdSolution

            Glide.with(this@ResultActivity).load("${Tools().MAIN_URL}/upload/${result.cropsImage}")
                .error(Glide.with(this@ResultActivity).load(R.drawable.no_image))
                .apply(
                    RequestOptions()
                        .signature(ObjectKey(System.currentTimeMillis()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                )
                .into(resultImg)
        }
    }
}