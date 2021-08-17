package com.deuksoft.tamjiat.ui.dashboard

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.deuksoft.tamjiat.HTTPManager.dtoManager.testDTO
import com.deuksoft.tamjiat.activity.main.MainActivity
import com.deuksoft.tamjiat.databinding.FragmentDashboardBinding
import com.deuksoft.tamjiat.itemAdapter.cropsAdapter

class DashboardFragment : Fragment(), MainActivity.onKeyBackPressedListener {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var item1 = testDTO("ftsf")
        var item2 = testDTO("asd")
        var item3 = testDTO("asd")

        var adapter = cropsAdapter()
        adapter.addCardItem(item1)
        adapter.addCardItem(item2)
        adapter.addCardItem(item3)

        adapter.setElevation(0.6f)
        binding.cardViewPager.setAdapter(adapter)
        binding.cardViewPager.isShowShadowTransformer(true)

        numCounter(0, 80, 0) //harvestProcess
        numCounter(0, 60, 1) //temp
        numCounter(0, 90, 2) //humidity
        numCounter(0, 12, 3) //wind
        numCounter(0, 5, 4) //totalNum
        numCounter(0, 3, 5) //crop1
        numCounter(0, 1, 6) //crop2
        numCounter(0, 1, 7) //crop3
        return root
    }

    /**
     * 카운트 애니메이션을 작동시키는 실질적은 메서드이다.
     * duration값에 목표하는 초에 맞춰 값이 오르게 된다.
     */
    private fun numCounter(start: Int, end: Int, flag : Int){

        var animation = ValueAnimator.ofInt(start, end).apply {
            duration = 1500
        }
        animation.addUpdateListener {value->
            when(flag){
                0->{
                    binding.apply {
                        harvestPerc.text = let { "${value.animatedValue} %" }
                        harvestProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                1 ->{
                    binding.apply {
                        tempCounter.text = let { "${value.animatedValue}℃" }
                        tempProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                2 ->{
                    binding.apply {
                        humCounter.text = let { "${value.animatedValue} %" }
                        humidityProgress.progress = let { value.animatedValue.toString().toInt() }
                    }
                }
                3->{
                    binding.windCounter.text = let {"${value.animatedValue} m/s" }
                }
                4 ->{
                    binding.totalNum.text =  let { value.animatedValue.toString()}
                }
                5 ->{
                    binding.crop1.text =  let { value.animatedValue.toString()}
                }
                6 ->{
                    binding.crop2.text =  let { value.animatedValue.toString()}
                }
                7 ->{
                    binding.crop3.text =  let { value.animatedValue.toString()}
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

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivity).setOnKeyBackPressedListener(this)
    }
}