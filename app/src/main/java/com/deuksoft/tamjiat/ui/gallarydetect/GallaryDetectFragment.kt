package com.deuksoft.tamjiat.ui.gallarydetect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.ActivityIntroBinding.inflate
import com.deuksoft.tamjiat.databinding.FragmentCameradetectBinding
import com.deuksoft.tamjiat.databinding.FragmentGallarydetectBinding

class GallaryDetectFragment: Fragment() {
    private var _gallaryBinding: FragmentGallarydetectBinding? = null
    private lateinit var gallaryDetectViewModel: GallaryDetectViewModel
    private val gallaryBinding get() = _gallaryBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gallaryDetectViewModel = ViewModelProvider(this).get(GallaryDetectViewModel::class.java)
        _gallaryBinding = FragmentGallarydetectBinding.inflate(inflater, container, false)

        gallaryBinding.gallaryDetectViewModel = gallaryDetectViewModel
        gallaryBinding.lifecycleOwner = this

        return gallaryBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("destroy", "hfds")
        _gallaryBinding = null
    }
}