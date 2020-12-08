package com.example.projeto

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projeto.api.EndPoints
import com.example.projeto.api.OutputPost
import com.example.projeto.api.ServiceBuilder
import com.example.projeto.api.problemas
import kotlinx.android.synthetic.main.activity_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteActivity : AppCompatActivity() {
    private var iduser : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
    }

    fun deleteMarker(view: View) {
        iduser = userid.text.toString().toInt()
        val request = ServiceBuilder.buildService(EndPoints::class.java)     // crio o request
        val call = request.deleteProblema(iduser)     // id a remover

        call.enqueue(object : Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                    if (response.body()?.status == false) {
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@DeleteActivity, "Remoção do marcador falhou, verifique o id do marcador...", Toast.LENGTH_SHORT).show()
                    }else{
                        val a: OutputPost = response.body()!!
                        val intent = Intent(this@DeleteActivity, MapsActivity::class.java)
                        Toast.makeText(this@DeleteActivity, "Marcador removido com sucesso", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@DeleteActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}