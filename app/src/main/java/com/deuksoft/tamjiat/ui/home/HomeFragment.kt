package com.deuksoft.tamjiat.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.deuksoft.tamjiat.GISManager.GetMyLocation
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropWeekDTO
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.activity.openSource.OpenSourceActivity
import com.deuksoft.tamjiat.databinding.FragmentHomeBinding
import com.deuksoft.tamjiat.itemAdapter.CropWeekAdapter
import com.kakao.sdk.user.UserApiClient

class HomeFragment : Fragment(), MainActivity.onKeyBackPressedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val homeBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeBinding.apply {
            homeViewModel = homeViewModel
            lifecycleOwner = this@HomeFragment
        }
        homeBinding.appOpenSource.setOnClickListener {
            startActivity(Intent(requireContext(), OpenSourceActivity::class.java))
        }
        setInitWeather()
        initUserInfo()
        setUserCropInfo()
        return homeBinding.root
    }

    private fun setInitWeather(){
        homeViewModel.getTodayWeather(GetMyLocation().getLocation(requireContext())).observe(viewLifecycleOwner){
            homeBinding.weatherAnimation.setAnimation("weather_${it.currWeatherIcon}.json")
            homeBinding.tempTxt.text = it.currTemp
        }

        homeViewModel.getWeatherFailedMessage().observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    //카카오 회원정보 불러와 화면에 셋팅하는 부분
    private fun initUserInfo(){
        homeViewModel.userName.observe(viewLifecycleOwner){
            homeBinding.userNameTxt.text = "$it 님!"
            setNameColor(it)
        }

        //외부 링크로 된 사진을 불러오는 작업을 하는 구간
        homeViewModel.userImageUrl.observe(viewLifecycleOwner){
            Glide.with(requireContext()).load(it)
                .error(Glide.with(requireContext()).load(R.drawable.no_image))
                .apply(
                    RequestOptions()
                        .signature(ObjectKey(System.currentTimeMillis()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                )
                .into(homeBinding.userImage)
            var drawble = (requireContext().getDrawable(R.drawable.user_img_round)) as GradientDrawable
            homeBinding.userImage.apply {
                background = drawble
                clipToOutline = true
            }
        }
    }

    //회원별 작물 정보를 로드하는 부분
    private fun setUserCropInfo(){
        homeViewModel.getTotalCropsNum(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!).observe(viewLifecycleOwner){
            homeBinding.cropNum.text = it
        }

        homeViewModel.getCropWeek(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!).observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                homeBinding.apply {
                    mainCrop.isVisible = true
                    emptyCropList.isVisible = false
                }
                setCropWeek(it)
            }else{
                homeBinding.apply {
                    mainCrop.isVisible = false
                    emptyCropList.isVisible = true
                }
            }

        }
    }

    //특정 글씨 색 칠하는 부분
    fun setNameColor(userName : String){
        var content = homeBinding.userNameTxt.text.toString()
        var start = content.indexOf(userName)
        var end = start + userName.length
        var spannableString = SpannableString(content)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.userName, null)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        homeBinding.userNameTxt.text = spannableString
    }

    fun setCropWeek(cropList: List<CropWeekDTO>){
        var cropWeekAdapter = CropWeekAdapter(requireContext(), cropList){}
        homeBinding.mainCrop.apply {
            adapter = cropWeekAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        cropWeekAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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