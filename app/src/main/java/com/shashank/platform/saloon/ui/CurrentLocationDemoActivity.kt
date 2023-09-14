package com.shashank.platform.saloon.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.shashank.platform.saloon.R

class CurrentLocationDemoActivity : AppCompatActivity() {


    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_location_demo)


        // Initialize the Places SDK
        Places.initialize(applicationContext, "AIzaSyCOejrdA6Od52MC7gbIKCGTeFJeYc6fOY4")
        placesClient = Places.createClient(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkLocationPermission()) {
            getLastKnownLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            123
        )
    }

    private fun checkLocationPermission(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    reverseGeocodeLocation(location.latitude, location.longitude)
                } else {
                    // Handle case where last known location is not available
                }
            }
    }


    private fun reverseGeocodeLocation(latitude: Double, longitude: Double) {
        val placeFields = listOf(Place.Field.ADDRESS)
        val request = FindCurrentPlaceRequest.builder(placeFields).build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        placesClient.findCurrentPlace(request)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val response = task.result
                    val likelihood = response?.placeLikelihoods?.get(0)
                    val address = likelihood?.place?.address

                    Log.d("reverseGeocodeLocation", "reverseGeocodeLocation: $address")
                    // Process the address data
                } else {
                    val exception = task.exception
                    // Handle error
                }
            }
    }


}