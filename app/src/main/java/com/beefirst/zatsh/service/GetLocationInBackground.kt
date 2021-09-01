package com.beefirst.zatsh.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class GetLocationInBackground : Service() {
    private var mLocationManager: LocationManager? = null

    inner class LocationListener(provider: String) :
        android.location.LocationListener {
        var mLastLocation: Location? = null
        override fun onLocationChanged(location: Location) {

            Log.e(TAG, "onLocationChanged: $location")

            mLastLocation?.set(location)

            Log.i(TAG, location.latitude.toString() + " <<<<")
            Log.i(TAG, location.longitude.toString() + " <<<<")

            val i = Intent("location_changed")
            i.putExtra("lat", location.latitude.toString())
            i.putExtra("lng", location.longitude.toString())
            sendBroadcast(i)
        }

        override fun onProviderDisabled(provider: String) {
            Log.e(TAG, "onProviderDisabled: $provider")
        }

        override fun onProviderEnabled(provider: String) {
            Log.e(TAG, "onProviderEnabled: $provider")
        }

        override fun onStatusChanged(
            provider: String,
            status: Int,
            extras: Bundle
        ) {
            Log.e(TAG, "onStatusChanged: $provider")
        }

        init {
            Log.e(TAG, "LocationListener $provider")
            mLastLocation = Location(provider)
        }
    }

    var mLocationListeners =
        arrayOf(
            LocationListener(LocationManager.GPS_PROVIDER),
            LocationListener(LocationManager.NETWORK_PROVIDER)
        )

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        initializeLocationManager()
        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                LOCATION_INTERVAL.toLong(),
                LOCATION_DISTANCE,
                mLocationListeners[1]
            )
        } catch (ex: SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }
        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_INTERVAL.toLong(),
                LOCATION_DISTANCE,
                mLocationListeners[0]
            )
        } catch (ex: SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }
    }

    private fun initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager")
        if (mLocationManager == null) {
            mLocationManager =
                applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    companion object {
        private const val TAG = "BOOMBOOMTESTGPS"
        private const val LOCATION_INTERVAL = 10000
        private const val LOCATION_DISTANCE = 0f
    }
}