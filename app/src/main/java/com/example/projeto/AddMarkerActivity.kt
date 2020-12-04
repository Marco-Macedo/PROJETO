package com.example.projeto

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
import retrofit2.Response


class AddMarkerActivity : AppCompatActivity() {
    private var userid : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_marker)

        var latitude =  intent.getStringExtra("latitude")
        var longitude = intent.getStringExtra("longitude")


        userid = intent.getIntExtra("user_id",0)
        findViewById<TextView>(R.id.Latitude).setText(latitude)
        findViewById<TextView>(R.id.Longitude).setText(longitude)
        findViewById<TextView>(R.id.user_id).setText(userid.toString())
    }

    fun adicionarmarcador(view: View) {

        val descr = descricao.text.toString().trim()
        val latitude = Latitude.text.toString().trim()
        val longitude = Longitude.text.toString().trim()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postRegistMarker(descr,latitude,longitude,userid)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////// REGISTAR MARCADOR ///////////////////////////////////

        call.enqueue(object : retrofit2.Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                        val intent = Intent(this@AddMarkerActivity, MapsActivity::class.java)
                        Toast.makeText(this@AddMarkerActivity, "Novo Marcador inserido com sucesso", Toast.LENGTH_SHORT).show()
                        intent.putExtra("user_id",userid)
                        startActivity(intent)
                    }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@AddMarkerActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }


}