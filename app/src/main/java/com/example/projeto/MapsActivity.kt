package com.example.projeto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapters.TitleAdapter
import com.example.projeto.api.*
import com.example.projeto.viewModel.TitleViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.security.auth.callback.Callback
import retrofit2.Call
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var problems: List<Problem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // call the service and add markers
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getProblem()
        var position: LatLng

        call.enqueue(object : retrofit2.Callback<List<Problem>> {
            override fun onResponse(call: Call<List<Problem>>, response: Response<List<Problem>>) {
                if (response.isSuccessful){     // Em caso de sucesso
                    problems = response.body()!! // igualar a lista de problemas
                    for (problem in problems) {
                        position = LatLng(problem.lat.toDouble(), //latlng precisa de recerber um double da longitude e latitude
                                problem.lng.toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title(problem.descr))
                    }
                }
            }
            override fun onFailure(call: Call<List<Problem>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val barcelos = LatLng(41.5166646, -8.6166642)
        //mMap.addMarker(MarkerOptions().position(barcelos).title("Marker in Barcelos"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(barcelos))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R. menu.menu_login, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.logout -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}