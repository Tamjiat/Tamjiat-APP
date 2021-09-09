package com.deuksoft.tamjiat.activity.openSource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deuksoft.tamjiat.databinding.ActivityOpenSourceBinding

class OpenSourceActivity : AppCompatActivity() {
    lateinit var openSourceBinding: ActivityOpenSourceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openSourceBinding = ActivityOpenSourceBinding.inflate(layoutInflater)
        setContentView(openSourceBinding.root)

        val openSource = "opensource.md"
        openSourceBinding.markdown.loadMarkdownFromAssets(openSource)

    }
}