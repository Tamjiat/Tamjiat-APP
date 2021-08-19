package com.deuksoft.tamjiat.GISManager

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GetMyLocation {
    var gps = hashMapOf<String, Double>()
    var gpsLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            gps["lat"] = location.latitude
            gps["lon"]  = location.longitude
        }
    }
    fun getLocation(context: Context): HashMap<String, Double> {
        var lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )
        } else {
            val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                gps["lat"] = location.latitude
                gps["lon"]  = location.longitude
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 1f, gpsLocationListener)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1f, gpsLocationListener)
        }
        return gps
    }
}