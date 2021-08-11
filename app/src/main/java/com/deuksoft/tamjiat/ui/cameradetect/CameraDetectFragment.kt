package com.deuksoft.tamjiat.ui.cameradetect

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.databinding.FragmentCameradetectBinding
import java.lang.Exception

class CameraDetectFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener, MainActivity.onKeyBackPressedListener {

    private lateinit var cameraDetectViewModel: CameraDetectViewModel
    private var _cameraBinding: FragmentCameradetectBinding? = null
    private val cameraBinding get() = _cameraBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cameraDetectViewModel = ViewModelProvider(this).get(CameraDetectViewModel::class.java)
        _cameraBinding = FragmentCameradetectBinding.inflate(inflater, container, false)

        cameraBinding.cameraDetectViewModel = cameraDetectViewModel
        cameraBinding.lifecycleOwner = this
        startCamera()
        init()
        cameraBinding.cropsName.onItemSelectedListener = this
        cameraBinding.scanWait.setOnClickListener(this)
        return cameraBinding.root
    }

    private fun init(){
        var cropsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.cropNames))
        cameraBinding.cropsName.adapter = cropsAdapter
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var s = parent?.getItemAtPosition(position).toString()
        Log.e("items", s)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onClick(v: View?) {
        when(v?.id){
            cameraBinding.scanWait.id->{
                cameraBinding.scanWait.visibility = View.GONE
                cameraBinding.noticeTxt.visibility = View.GONE
                cameraBinding.farmNum.visibility = View.GONE
                cameraBinding.farmNumTxt.visibility = View.GONE
                cameraBinding.scanAnimation.visibility = View.GONE
                cameraBinding.photographerName.visibility = View.GONE
                cameraBinding.photographerNameTxt.visibility = View.GONE
                cameraBinding.cropsName.visibility = View.INVISIBLE
                cameraBinding.captureBtn.visibility = View.VISIBLE
            }
        }
    }
    override fun onBackKey() {
        var activity = activity as MainActivity
        activity.apply {
            setOnKeyBackPressedListener(null)
            onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).setOnKeyBackPressedListener(this)
    }
}