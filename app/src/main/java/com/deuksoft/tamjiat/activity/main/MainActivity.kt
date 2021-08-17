package com.deuksoft.tamjiat.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.ActivityMainBinding
import com.deuksoft.tamjiat.ui.cameradetect.CameraDetectFragment
import com.deuksoft.tamjiat.ui.dashboard.DashboardFragment
import com.deuksoft.tamjiat.ui.gallarydetect.GallaryDetectFragment
import com.deuksoft.tamjiat.ui.home.HomeFragment
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.util.*

class MainActivity : AppCompatActivity(), AnimatedBottomBar.OnTabSelectListener {

    private lateinit var mainBinding: ActivityMainBinding
    private var onKeyBackPressed :onKeyBackPressedListener? = null
    var isBackKey = false
    var tabList = Stack<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.navView.setOnTabSelectListener(this)
    }
    override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?, newIndex: Int,newTab: AnimatedBottomBar.Tab) {
        var fragment = Fragment()
        if(!isBackKey) tabList.add(lastIndex)
        when(newTab.id){
            R.id.navigation_home->{
                fragment = HomeFragment()
            }
            R.id.navigation_dashboard->{
                fragment = DashboardFragment()
            }
            R.id.navigation_cameradetect ->{
                fragment = CameraDetectFragment()
            }
            R.id.navigation_gallarydetect ->{
                fragment = GallaryDetectFragment()
            }
        }
        fragment.let {
            supportFragmentManager.apply {
                beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).addToBackStack(null).commit()
            }
        }
        isBackKey = false
    }
    /*override fun onTabIntercepted(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?, newIndex: Int, newTab: AnimatedBottomBar.Tab): Boolean {

        return true
    }*/

    interface onKeyBackPressedListener{
        fun onBackKey()
    }

    fun setOnKeyBackPressedListener(onKeyBackPressedListener: onKeyBackPressedListener?){
        onKeyBackPressed = onKeyBackPressedListener
    }

    override fun onBackPressed() {
        isBackKey = true
        if(onKeyBackPressed != null){
            onKeyBackPressed?.onBackKey()
        }else{
            if(supportFragmentManager.backStackEntryCount == 0) {
                finishAffinity()
            }else{
                super.onBackPressed()
                /*Log.e("3", "3")
                Log.e("fd", tabList.size.toString())
                Log.e("fdfddd", supportFragmentManager.backStackEntryCount.toString())
                if(tabList.size > 0) mainBinding.navView.selectTabAt(tabList.pop())*/
            }
        }

    }

    fun setNavEnable(state :Boolean){
        for(i in 0..3){
            mainBinding.navView.setTabEnabledAt(i, state)
        }
    }

}