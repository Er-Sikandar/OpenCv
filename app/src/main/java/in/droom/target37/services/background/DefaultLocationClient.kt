package `in`.droom.target37.services.background

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import `in`.droom.target37.utils.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(private val context: Context,private val client: FusedLocationProviderClient) : LocationClient {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
      return callbackFlow @androidx.annotation.RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION]) {
          /**
           * Check Location Permission
           */
          if (!context.hasLocationPermission()){
              throw LocationClient.LocationException("Missing Location Permission")
          }
          val locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
          val isGpsEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
          val isNetworkEnable=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
           if (!isGpsEnable && !isNetworkEnable){
               throw LocationClient.LocationException("GPS is Disabled")
           }
           val request= LocationRequest.create()
               .setInterval(interval)
               .setFastestInterval(interval)

          /**
           * Callback for Location Update
           */
          val locationCallBack=object : LocationCallback(){
              override fun onLocationResult(result: LocationResult) {
                  super.onLocationResult(result)
                  result.locations.lastOrNull()?.let {
                      launch { send(it) }
                  }
              }
          }

          client.requestLocationUpdates(request,locationCallBack,
              Looper.getMainLooper())
          /**
           * Remove Location Update
           */
        awaitClose { client.removeLocationUpdates (locationCallBack) }
      }
    }
}