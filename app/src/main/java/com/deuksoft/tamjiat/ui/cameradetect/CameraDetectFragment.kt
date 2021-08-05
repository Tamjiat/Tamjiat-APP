package com.deuksoft.tamjiat.ui.cameradetect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.FragmentCameradetectBinding
import java.lang.Exception

class CameraDetectFragment : Fragment() {

    private lateinit var cameraDetectViewModel: CameraDetectViewModel
    private var _cameraBinding: FragmentCameradetectBinding? = null
    private val cameraBinding get() = _cameraBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cameraDetectViewModel = ViewModelProvider(this).get(CameraDetectViewModel::class.java)
        _cameraBinding = FragmentCameradetectBinding.inflate(inflater, container, false)

        cameraBinding.cameraDetectViewModel = cameraDetectViewModel
        cameraBinding.lifecycleOwner = this
        startCamera()

        return cameraBinding.root
    }

    private fun startCamera(){
        var cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraBinding.camera.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try{
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            }catch (e : Exception){
                Log.e("Camere Error", "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _cameraBinding = null
    }
}