package com.deuksoft.tamjiat.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.GISManager.GetMyLocation
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.databinding.FragmentHomeBinding
import com.kakao.sdk.user.UserApiClient

class HomeFragment : Fragment(), MainActivity.onKeyBackPressedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val homeBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeBinding.homeViewModel = homeViewModel
        homeBinding.lifecycleOwner = this
        setInitWeather()
        getUserName()
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

    private fun getUserName(){
        homeViewModel.userName.observe(viewLifecycleOwner){
            homeBinding.userNameTxt.text = it
        }
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