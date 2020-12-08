package com.example.projeto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.projeto.api.EndPoints
import com.example.projeto.api.OutputPost
import com.example.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.add_marker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddMarkerActivity : AppCompatActivity() {
    private var userid : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_marker)

        var latitude =  intent.getStringExtra("latitude")
        var longitude = intent.getStringExtra("longitude")

        var token = getSharedPreferences("id", Context.MODE_PRIVATE)
        userid = token.getInt("id_login_atual", 0)

        findViewById<TextView>(R.id.lat).setText(latitude)
        findViewById<TextView>(R.id.lng).setText(longitude)
        findViewById<TextView>(R.id.iduser).setText(userid.toString())
    }

    fun addMarker(view: View) {

        val descr = descr.text.toString().trim()
        val latitude = lat.text.toString().trim()
        val longitude = lng.text.toString().trim()

               val request = ServiceBuilder.buildService(EndPoints::class.java)
               val call = request.postRegister(descr,latitude,longitude,userid)


       ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               //////////////////////////// REGISTAR MARCADOR ///////////////////////////////////

               call.enqueue(object : Callback<OutputPost> {

                   override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                       if (response.isSuccessful) {
                               val intent = Intent(this@AddMarkerActivity, MapsActivity::class.java)
                               Toast.makeText(this@AddMarkerActivity, "Novo Marcador inserido com sucesso", Toast.LENGTH_SHORT).show()
                               intent.putExtra("user_id",userid)
                               startActivity(intent)

                       }
                   }
                   override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                       Toast.makeText(this@AddMarkerActivity, "Erro na inserção", Toast.LENGTH_SHORT).show()
                   }
               })

    }


}