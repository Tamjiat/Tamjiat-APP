package com.deuksoft.tamjiat.ui.dashboard

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.GISManager.GetMyLocation
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropDetailDTO
import com.deuksoft.tamjiat.HTTPManager.DTOManager.CropSummaryDTO
import com.deuksoft.tamjiat.SaveInfoManager.UserInfo
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.databinding.FragmentDashboardBinding
import com.deuksoft.tamjiat.itemAdapter.CropsAdapter

class DashboardFragment : Fragment(), MainActivity.onKeyBackPressedListener, AdapterView.OnItemSelectedListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _dashBoardBinding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val dashBoardBinding get() = _dashBoardBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _dashBoardBinding = FragmentDashboardBinding.inflate(inflater, container, false)

        setUserCrops()
        setCropPercent()

        dashBoardBinding.cropList.onItemSelectedListener = this
        return dashBoardBinding.root
    }

    /**
     * 카운트 애니메이션을 작동시키는 실질적은 메서드이다.
     * duration값에 목표하는 초에 맞춰 값이 오르게 된다.
     */
    private fun setUserCrops(){
        dashboardViewModel.getCropDetail(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!).observe(viewLifecycleOwner){
                cropList -> setCropAdapter(cropList)
        }
        dashboardViewModel.getCropSummary(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!).observe(viewLifecycleOwner){
            summaryList-> setCropSummary(summaryList)
        }
    }

    private fun setCropSummary(summaryList: List<CropSummaryDTO>){
        if(summaryList.isNotEmpty()){
            for(item in summaryList){
                when(item.category){
                    "노지" -> numCounter(0, item.count, 5) //crop1
                    "하우스" -> numCounter(0, item.count, 6) //crop2
                    "고랭지" -> numCounter(0, item.count, 7) //crop3
                    "total" -> numCounter(0, item.count, 4) //totalNum
                }
            }
        }else{
            numCounter(0, 0, 4) //totalNum
            numCounter(0, 0, 5) //crop1
            numCounter(0, 0, 6) //crop2
            numCounter(0, 0, 7) //crop3
        }
    }

    private fun setCropPercent(){
        dashboardViewModel.getCropCategory(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!).observe(viewLifecycleOwner){
            var cropList = arrayListOf<String>()
            for(item in it){
                cropList.add(item.cropsName)
            }
            Log.e("sfsd", cropList.toString())
            var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cropList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dashBoardBinding.cropList.adapter = adapter

        }
    }

    private fun setCropAdapter(cropList: List<CropDetailDTO>){
        if(cropList.isNotEmpty()){
            var cropsAdapter = CropsAdapter()
            cropsAdapter.setElevation(0.6f)
            dashBoardBinding.apply {
                cardViewPager.isVisible = true
                emptyCropList.isVisible = false
            }
            for(item in cropList){
                cropsAdapter.addCardItem(item)
            }
            dashBoardBinding.cardViewPager.apply {
                setAdapter(cropsAdapter)
                isShowShadowTransformer(true)
            }
        }else{
            dashBoardBinding.apply {
                cardViewPager.isVisible = false
                emptyCropList.isVisible = true
            }
        }
    }

    private fun numCounter(start: Int, end: Int, flag : Int){

        var animation = ValueAnimator.ofInt(start, end).apply {
            duration = 1500
        }
        animation.addUpdateListener {value->
            when(flag){
                0->{
                    dashBoardBinding.apply {
                        harvestPerc.text = let { "${value.animatedValue} %" }
                        harvestProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                1 ->{
                    dashBoardBinding.apply {
                        tempCounter.text = let { "${value.animatedValue}℃" }
                        tempProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                2 ->{
                    dashBoardBinding.apply {
                        humCounter.text = let { "${value.animatedValue} %" }
                        humidityProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                3->{
                    dashBoardBinding.windCounter.text = let {"${value.animatedValue} m/s" }
                }
                4 ->{
                    dashBoardBinding.totalNum.text =  let { value.animatedValue.toString()}
                }
                5 ->{
                    dashBoardBinding.crop1.text =  let { value.animatedValue.toString()}
                }
                6 ->{
                    dashBoardBinding.crop2.text =  let { value.animatedValue.toString()}
                }
                7 ->{
                    dashBoardBinding.crop3.text =  let { value.animatedValue.toString()}
                }
            }
        }

        //애니메이션이 끝날때 다음작업을 해야 오류발생을 하지 않는다.
        animation.doOnStart {
            (activity as MainActivity).setNavEnable(false)
        }
        animation.doOnEnd {
            (activity as MainActivity).setNavEnable(true)
        }
        animation.start()
    }

    private fun getCropPercent(cropName : String){
        dashboardViewModel.getCropPercent(UserInfo(requireContext()).getUserInfo()["USER_ID"]!!, cropName, GetMyLocation().getLocation(requireContext())).observe(viewLifecycleOwner){
            numCounter(0, it.cropPercent.percent, 0) //harvestProcess
            numCounter(0, it.weather.temp, 1) //temp
            numCounter(0, it.weather.humidity, 2) //humidity
            numCounter(0, it.weather.windSpeed, 3) //wind
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _dashBoardBinding = null
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getCropPercent(dashBoardBinding.cropList.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}