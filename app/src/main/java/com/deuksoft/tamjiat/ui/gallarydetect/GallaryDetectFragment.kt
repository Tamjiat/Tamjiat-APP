package com.deuksoft.tamjiat.ui.gallarydetect

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.activity.result.ResultActivity
import com.deuksoft.tamjiat.databinding.FragmentGallarydetectBinding
import java.lang.Exception


class GallaryDetectFragment: Fragment(), MainActivity.onKeyBackPressedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var _gallaryBinding: FragmentGallarydetectBinding? = null
    private lateinit var gallaryDetectViewModel: GallaryDetectViewModel
    private val gallaryBinding get() = _gallaryBinding!!
    private lateinit var cropsName : String
    lateinit var progressDialog : ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        gallaryDetectViewModel = ViewModelProvider(this).get(GallaryDetectViewModel::class.java)
        _gallaryBinding = FragmentGallarydetectBinding.inflate(inflater, container, false)

        gallaryBinding.gallaryDetectViewModel = gallaryDetectViewModel
        gallaryBinding.lifecycleOwner = this
        init()
        gallaryBinding.imageSelectBtn.setOnClickListener(this)
        gallaryBinding.uploadBtn.setOnClickListener(this)
        gallaryBinding.cropsName.onItemSelectedListener = this

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("분석 중...")
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        return gallaryBinding.root
    }

    private fun init(){
        var cropsAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, resources.getStringArray(
            com.deuksoft.tamjiat.R.array.cropNames))
        gallaryBinding.cropsName.adapter = cropsAdapter
    }

    fun checkPerm(){
        when {
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                //권한이 잘 부여 되었을 경우
                navigatePhotos()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                //권한 허용을 하지않고 한번더 할 경우 교육용 팝업을 실행해야한다.
                showPermissionContextPopup()
            }
            else -> {
                //권한을 요청하는 함수
                var permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, 1000)
            }
        }
    }

    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, 2000)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다")
            .setMessage("탐지앗 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    private fun sendImage(){
        progressDialog.show()
        var drawble = gallaryBinding.uploadImg.drawable as BitmapDrawable
        gallaryDetectViewModel.sendGallayImg(drawble.bitmap, UserInfo(requireContext()).getUserInfo()["USER_ID"]!!, cropsName, gallaryBinding.beedNameTxt.text.toString()).observe(viewLifecycleOwner){
            Log.e("resultLog", it.toString())
            progressDialog.dismiss()
            var intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra("result", it.result)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            2000 -> {
                if (data!!.data != null) {
                    try{
                        var input = requireContext().contentResolver.openInputStream(data.data!!)
                        var img = BitmapFactory.decodeStream(input)

                        gallaryBinding.uploadImg.setImageBitmap(img)

                        gallaryBinding.apply {
                            imageLayout.visibility = View.INVISIBLE
                            uploadImg.isVisible = true
                            uploadBtn.isVisible = true
                            reSelectBtn.isVisible = true
                            imageSelectBtn.isVisible = false
                        }
                    }catch (e :Exception){
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(requireContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(requireContext(), "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한이 허용 된거임
                    navigatePhotos()
                } else {
                    Toast.makeText(requireContext(), "권한을 거부 하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("destroy", "hfds")
        _gallaryBinding = null
    }
    override fun onBackKey() {
        var activity = activity as MainActivity
        activity.apply {
            setOnKeyBackPressedListener(null)
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).setOnKeyBackPressedListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            gallaryBinding.imageSelectBtn.id->{
                checkPerm()
            }
            gallaryBinding.uploadBtn.id->{
                sendImage()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        cropsName = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}