package com.example.projeto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import com.example.projeto.api.EndPoints
import com.example.projeto.api.OutputPost
import com.example.projeto.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_login2.*
import java.net.CacheResponse
import javax.security.auth.callback.Callback
import retrofit2.Call
import retrofit2.Response
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
    }

    fun notas(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun login(view: View) {
        val username = nome.text.toString().trim()
        val password = senha.text.toString().trim()

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postLogin(username,password)

        call.enqueue(object : retrofit2.Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                if (response.isSuccessful) {
                    if (response.body()?.error == false) {
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@Login, "Login falhou, credenciais erradas.", Toast.LENGTH_SHORT).show()
                    }else{
                        val intent = Intent(this@Login, MapsActivity::class.java)
                        Toast.makeText(this@Login, "Login efectuado", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@Login, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}