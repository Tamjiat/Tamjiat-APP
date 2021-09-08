package com.deuksoft.tamjiat.ui.cameradetect

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.core.impl.ImageCaptureConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.activity.result.ResultActivity
import com.deuksoft.tamjiat.databinding.FragmentCameradetectBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

class CameraDetectFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener, MainActivity.onKeyBackPressedListener {

    private lateinit var cameraDetectViewModel: CameraDetectViewModel
    private var _cameraBinding: FragmentCameradetectBinding? = null
    private val cameraBinding get() = _cameraBinding!!
    private lateinit var imageCapture: ImageCapture
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraAnimationListener: Animation.AnimationListener
    private var saveUri : Uri? = null
    private lateinit var cropsName : String
    lateinit var progressDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cameraDetectViewModel = ViewModelProvider(this).get(CameraDetectViewModel::class.java)
        _cameraBinding = FragmentCameradetectBinding.inflate(inflater, container, false)

        cameraBinding.cameraDetectViewModel = cameraDetectViewModel
        cameraBinding.lifecycleOwner = this
        startCamera()
        setCameraAnimationListener()
        init()
        outputDirectory = getOutputDirectory()

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("분석 중...")
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        cameraBinding.cropsName.onItemSelectedListener = this
        cameraBinding.scanWait.setOnClickListener(this)
        cameraBinding.backBtn.setOnClickListener(this)
        cameraBinding.captureBtn.setOnClickListener(this)
        cameraBinding.reCapture.setOnClickListener(this)
        cameraBinding.photoSendBtn.setOnClickListener(this)
        return cameraBinding.root
    }

    private fun init(){
        var cropsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.cropNames))
        cameraBinding.cropsName.adapter = cropsAdapter
    }

    //카메라 시작 부분 프로바이더 구현
    private fun startCamera(){
        var cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraBinding.camera.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            }catch (e : Exception){
                Log.e("Camere Error", "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    //사진 미리보기를 위해서 임시로 사진을 저장해 놓는다.
    private fun savePhoto() {
        imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat("yy-mm-dd", Locale.KOREA).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOption = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOption,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    saveUri = Uri.fromFile(photoFile)

                    //카메라 찍는 애니메이션을 표현하기 위한 부분이다.
                    var animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.camera_shutter)
                    animation.setAnimationListener(cameraAnimationListener)
                    cameraBinding.frameLayoutShutter.apply {
                        animation = animation
                        isVisible = true
                        startAnimation(animation)
                    }
                }
                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                }

            })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _cameraBinding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        cropsName = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onClick(v: View?) {
        when(v?.id){
            cameraBinding.scanWait.id->{
                cameraBinding.apply {
                    scanWait.visibility = View.GONE
                    noticeTxt.visibility = View.GONE
                    beedName.visibility = View.GONE
                    beedNameTxt.visibility = View.GONE
                    scanAnimation.visibility = View.GONE
                    photographerName.visibility = View.GONE
                    photographerNameTxt.visibility = View.GONE
                    backBtn.visibility = View.VISIBLE
                    cropsName.visibility = View.INVISIBLE
                    captureBtn.visibility = View.VISIBLE
                }
            }
            cameraBinding.backBtn.id -> {
                cameraBinding.apply {
                    scanWait.visibility = View.VISIBLE
                    noticeTxt.visibility = View.VISIBLE
                    beedName.visibility = View.VISIBLE
                    beedNameTxt.visibility = View.VISIBLE
                    scanAnimation.visibility = View.VISIBLE
                    photographerName.visibility = View.VISIBLE
                    photographerNameTxt.visibility = View.VISIBLE
                    backBtn.visibility = View.GONE
                    cropsName.visibility = View.VISIBLE
                    captureBtn.visibility = View.INVISIBLE
                }
            }
            cameraBinding.captureBtn.id ->{
                savePhoto()
            }
            cameraBinding.reCapture.id->{
                hideCaptureImage()
            }
            cameraBinding.photoSendBtn.id->{
                sendPhoto()
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


    //카메라 촬영 애니메이션이 끝나면 이미지 미리보기 기능 실행 할 수 있게 리스너 구현
    private fun setCameraAnimationListener() {
        cameraAnimationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                showCaptureImage()
            }
            override fun onAnimationRepeat(animation: Animation?) {

            }

        }
    }
    private fun showCaptureImage(): Boolean {
        if (cameraBinding.frameLayoutPreview.visibility == View.GONE) {
            cameraBinding.apply {
                frameLayoutPreview.isVisible = true
                captureImg.setImageURI(saveUri)
            }
            return false
        }
        return true
    }

    private fun hideCaptureImage() {
        cameraBinding.apply {
            captureImg.setImageURI(null)
            frameLayoutPreview.isVisible = false
            frameLayoutShutter.isVisible = false
        }


    }
    private fun getOutputDirectory(): File {
        val mediaDir = requireContext().externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun sendPhoto(){
        progressDialog.show()
        var drawble = cameraBinding.captureImg.drawable as BitmapDrawable
        cameraDetectViewModel.sendImage(drawble.bitmap, UserInfo(requireContext()).getUserInfo()["USER_ID"]!!,cropsName, cameraBinding.beedNameTxt.text.toString()).observe(viewLifecycleOwner){
            Log.e("resultLog", it.result.toString())
            progressDialog.dismiss()
            var intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra("result", it.result)
            startActivity(intent)
        }
    }

}