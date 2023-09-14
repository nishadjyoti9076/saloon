package com.shashank.platform.saloon.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.firebase.FirebaseApp
import com.shashank.platform.saloon.R


class DrawMapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private var originLatitude: Double = 28.5021359
    private var originLongitude: Double = 77.4054901
    private var destinationLatitude: Double = 28.5151087
    private var destinationLongitude: Double = 77.3932163
    var button: Button? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_map)
        button = findViewById(R.id.directions)

        // val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        /*FirebaseApp.initializeApp(this@DrawMapActivity)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result

                Log.d("FCMtoken", token)

            })*/

        /*   val ai: ApplicationInfo = applicationContext.packageManager
               .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
           val value = ai.metaData["AIzaSyCOejrdA6Od52MC7gbIKCGTeFJeYc6fOY4"]
           val apiKey = value.toString()

           // Initializing the Places API with the help of our API_KEY
           if (!Places.isInitialized()) {
               Places.initialize(applicationContext, apiKey)
           }
           // Map Fragment
           val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
           mapFragment.getMapAsync(this)
           button?.setOnClickListener {
               mapFragment.getMapAsync {
                   mMap = it
                   val originLocation = LatLng(originLatitude, originLongitude)
                   mMap.addMarker(MarkerOptions().position(originLocation))
                   val destinationLocation = LatLng(destinationLatitude, destinationLongitude)
                   mMap.addMarker(MarkerOptions().position(destinationLocation))
                   val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
                   GetDirection(urll).execute()
                   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 14F))
               }
           }

       }


       private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
           return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                   "&destination=${dest.latitude},${dest.longitude}" +
                   "&sensor=false" +
                   "&mode=driving" +
                   "&key=$secret"
       }


       @SuppressLint("StaticFieldLeak")
       private inner class GetDirection(val url: String) :
           AsyncTask<Void, Void, List<List<LatLng>>>() {
           override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
               val client = OkHttpClient()
               val request = Request.Builder().url(url).build()
               val response = client.newCall(request).execute()
               val data = response.body!!.string()

               val result = ArrayList<List<LatLng>>()
               try {
                   val respObj = Gson().fromJson(data, MapData::class.java)
                   val path = ArrayList<LatLng>()
                   for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                       path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                   }
                   result.add(path)
               } catch (e: Exception) {
                   e.printStackTrace()
               }
               return result
           }

           override fun onPostExecute(result: List<List<LatLng>>) {
               val lineoption = PolylineOptions()
               for (i in result.indices) {
                   lineoption.addAll(result[i])
                   lineoption.width(10f)
                   lineoption.color(Color.GREEN)
                   lineoption.geodesic(true)
               }
               mMap.addPolyline(lineoption)
           }
       }

       fun decodePolyline(encoded: String): List<LatLng> {
           val poly = ArrayList<LatLng>()
           var index = 0
           val len = encoded.length
           var lat = 0
           var lng = 0
           while (index < len) {
               var b: Int
               var shift = 0
               var result = 0
               do {
                   b = encoded[index++].code - 63
                   result = result or (b and 0x1f shl shift)
                   shift += 5
               } while (b >= 0x20)
               val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
               lat += dlat
               shift = 0
               result = 0
               do {
                   b = encoded[index++].code - 63
                   result = result or (b and 0x1f shl shift)
                   shift += 5
               } while (b >= 0x20)
               val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
               lng += dlng
               val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
               poly.add(latLng)
           }
           return poly
       }

       override fun onMapReady(p0: GoogleMap) {
           mMap = p0
           val originLocation = LatLng(originLatitude, originLongitude)
           mMap.clear()
           mMap.addMarker(MarkerOptions().position(originLocation))
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))

       }*/
    }

    override fun onMapReady(p0: GoogleMap) {
    }
}




