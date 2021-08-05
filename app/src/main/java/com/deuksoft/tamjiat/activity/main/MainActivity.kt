package com.deuksoft.tamjiat.activity.main

import android.graphics.Camera
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.deuksoft.tamjiat.R
import com.deuksoft.tamjiat.databinding.ActivityMainBinding
import com.deuksoft.tamjiat.ui.cameradetect.CameraDetectFragment
import com.deuksoft.tamjiat.ui.dashboard.DashboardFragment
import com.deuksoft.tamjiat.ui.gallarydetect.GallaryDetectFragment
import com.deuksoft.tamjiat.ui.gallarydetect.GallaryDetectViewModel
import com.deuksoft.tamjiat.ui.home.HomeFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity(), AnimatedBottomBar.OnTabInterceptListener {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.navView.setOnTabInterceptListener(this)


    }

    override fun onTabIntercepted(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?, newIndex: Int, newTab: AnimatedBottomBar.Tab): Boolean {
        var fragment = Fragment()
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
        return true
    }
}