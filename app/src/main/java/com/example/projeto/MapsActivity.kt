package com.example.projeto

import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapters.TitleAdapter
import com.example.projeto.api.*
import com.example.projeto.viewModel.TitleViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text
import javax.security.auth.callback.Callback
import retrofit2.Call
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var problems: List<problemas>

    //add to implement location periodic updates
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // added to implement location periodic updates
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private lateinit var lat : String
    private lateinit var lng : String
    private var userid : Int = 0

    // add variables to shared preferences
    private var idsharedpreference : Int = 0
    private lateinit var nomesharedpreference : String

    //added to implement distance between two locations
    private var continenteLat: Double = 0.0
    private var continenteLong: Double = 0.0

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1 // add implement location periodic updates
        private const val REQUEST_CHECK_SETTINGS = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //added to implement distance between two locations
        continenteLat = 41.53678
        continenteLong = -8.60905

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //initialize fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        userid =  intent.getIntExtra("userid",0)


///////////////////////////// call the service and add markers ///////////////////////////////////////////////////

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblem()
        var position: LatLng

        call.enqueue(object : retrofit2.Callback<List<problemas>> {
            override fun onResponse(call: Call<List<problemas>>, response: Response<List<problemas>>) {
                if (response.isSuccessful){     // Em caso de sucesso
                    problems = response.body()!! // igualar a lista de problemas
                    for (problem in problems) {
                        position = LatLng(problem.latitude.toDouble(), //latlng precisa de recerber um double da longitude e latitude
                                problem.longitude.toDouble())
                        mMap.addMarker(
                                MarkerOptions()
                                        .position(position)
                                        .snippet("ID do problema: " + problem.id.toString())
                                        .title(problem.descr)
                                        .anchor(0.5f, 0.5f)
                                        .zIndex(1.0f)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                }
            }
            override fun onFailure(call: Call<List<problemas>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        locationCallback = object : LocationCallback() {    // é disparada sempre que novas coordenadas sao recebidas
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                var token = getSharedPreferences("id", Context.MODE_PRIVATE)
                idsharedpreference = token.getInt("id_login_atual", 0)

                var token2 = getSharedPreferences("username", Context.MODE_PRIVATE)
                nomesharedpreference = token2.getString("username_login_atual"," ").toString()

                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                lat = loc.latitude.toString()
                lng = loc.longitude.toString()

                // preenche as coordenadas
                findViewById<TextView>(R.id.txtcoordenadas).setText("Lat: " + loc.latitude + " - Long: " + loc.longitude)
                Log.d("**** MARCO", "new location received - " + loc.latitude + " -" + loc.longitude)

                // distancia
                findViewById<TextView>(R.id.txtdistancia).setText("Distância: " + calculateDistance(
                        lastLocation.latitude, lastLocation.longitude,
                        continenteLat, continenteLong).toString())

                // morada
                val address = getAddress(lastLocation.latitude, lastLocation.longitude)
                findViewById<TextView>(R.id.txtmorada).setText("Morada: " + address)

                // user id
                findViewById<TextView>(R.id.USERID).setText("User ID: " + idsharedpreference.toString().toInt())

                // nome
                findViewById<TextView>(R.id.NOME).setText("Nome: " + nomesharedpreference)
            }
        }

        // request creation
        createLocationRequest() // Chama a funcao createlocationrequest()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val barcelos = LatLng(41.5166646, -8.6166642)
        //mMap.addMarker(MarkerOptions().position(barcelos).title("Marker in Barcelos"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelos))
        /*        mMap.setOnMarkerClickListener {
              Toast.makeText(this,"Entrei",Toast.LENGTH_SHORT).show()
              true
          }*/
        setUpMap()
    }

    fun setUpMap() {           // GET LOCATION

        // CHECKS FOR PERMISSIONS
        if (ActivityCompat.checkSelfPermission(this,  // existe permissoes?
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,    // allow retrofit to acces this device´s location??
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        } else {       // gets Last Known Location
            // 1
            mMap.isMyLocationEnabled = true
            // 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                // 3
                if (location != null) {
                    lastLocation = location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        // interval specifies the rate at which your app will like to receive updates.
        locationRequest.interval = 10000 // intervalo com que as coordenadas vao ser recebidas
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY // maxima precisao
    }

    override fun onPause() {    // aplicacao interrompida, parar de receber novas coordenadas ( ocupa processamento, bateria...)
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("**** MARCO", "onPause - removeLocationUpdates")
    }

    public override fun onResume() {        // ON RESUME EVENT
        super.onResume()
        startLocationUpdates()
        Log.d("**** MARCO", "onResume - startLocationUpdates")
    }

    private fun startLocationUpdates() { //
        if (ActivityCompat.checkSelfPermission(this,        // existe permissoes?
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]
    }



    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val list = geocoder.getFromLocation(lat, lng, 1)
        return list[0].getAddressLine(0)
    }


    // MENU DE OPCOES E AS SUAS FUNÇÕES //

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.logout -> {
                var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                var editor = token.edit()
                editor.putString("username_login_atual"," ")        // Iguala valor a vazio, fica sem valor, credenciais soltas
                editor.commit()                                     // Atualizar editor
                val intent = Intent(this@MapsActivity, Login::class.java)
                startActivity(intent)
                true
            }
            R.id.add -> {

                val intent2 = Intent(this, AddMarkerActivity::class.java)
                intent2.putExtra("latitude",lat)
                intent2.putExtra("longitude", lng)
                startActivity(intent2)
                true
            }
            R.id.delete -> {
                val intent = Intent(this, DeleteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}