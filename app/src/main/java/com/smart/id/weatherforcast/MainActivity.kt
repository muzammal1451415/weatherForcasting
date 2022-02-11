package com.smart.id.weatherforcast

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.smart.id.weatherforcast.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var locationServicesStatus = false
    lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onStart() {
        super.onStart()

        if(locationEnabled()){
                requestPermissions()
                getCurrentLocation()

        }else{
            openLocationServices()
        }
    }

    fun requestPermissions():Boolean{
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)

            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
            return false;
        }
        else{
            return true
        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        // Permissions are granted
                        getCurrentLocation()


                    }
                } else {
                    Toast.makeText(this, getString(R.string.text_permission_denied), Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
    fun openLocationServices(){
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.msg_please_enable_location_services))
            .setPositiveButton(getString(R.string.msg_on), DialogInterface.OnClickListener
            { paramDialogInterface, paramInt ->
                locationServicesStatus = true
                this.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            })
            .setNegativeButton(getString(R.string.msg_cancel), null)
            .setCancelable(false)
            .show()
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation()
    {
        if(locationEnabled()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        GlobalScope.launch(Dispatchers.Main) {
                            val id = "0bc9bc2a73fd9644f664cf5f5c5be8d7"
                            var weatherResponse = RetrofitClient.api.getWeatherResponse(it.latitude.toString(),it.longitude.toString(),id)
                            tv_loading.visibility = View.GONE
                            ll_weather_layout.visibility = View.VISIBLE
                            var details = weatherResponse.body()
                            tv_city.text = details?.name
                            tv_main.text = details!!.weather[0].main
                            tv_description.text = details!!.weather[0].description
                            tv_temprature.text = "Temprature (Celsius): ${details.main.temp}"
                            tv_feels_like.text = "Feels Like(Celsius): ${details.main.feels_like}"
                            tv_temp_max.text = "Max temp(Celsius): ${details.main.temp_max}"
                            tv_temp_min.text = "Min temp(Celsius): ${details.main.temp_min}"
                            tv_humidity.text = "Humidity(%): ${details.main.humidity}"
                            tv_sea_level.text = "Sea level: ${details.main.sea_level}"
                            tv_grnd_level.text = "Ground level: ${details.main.grnd_level}"
                            tv_pressure.text = "Pressure: ${details.main.pressure}"

                        }
                    }
                    if(location!=null){
                        Toast.makeText(this@MainActivity, "Please open Google maps application at once and then come back again", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }



                }.addOnFailureListener {

                    Toast.makeText(this@MainActivity, getString(R.string.msg_error_in_getting_current_location), Toast.LENGTH_SHORT).show()
                }
        }
        else{
            openLocationServices()
        }
    }
    private fun locationEnabled():Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}