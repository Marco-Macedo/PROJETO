package com.example.projeto

import android.content.Context
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
    private var userid : Int = 0
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////// LOGIN ///////////////////////////////////

        call.enqueue(object : retrofit2.Callback<OutputPost> {

            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {

                if (response.isSuccessful) {
                    if (response.body()?.cnt == 0) {
                        val c: OutputPost = response.body()!!
                        Toast.makeText(this@Login, "Login falhou, credenciais erradas.", Toast.LENGTH_SHORT).show()
                    }else{


                        val a: OutputPost = response.body()!!
                        val intent = Intent(this@Login, MapsActivity::class.java)
                        userid = a.id.toInt()
                        intent.putExtra("userid",userid)


                        /// GET NAME SHARED PREFERENCES ////

                        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
                        var editor = token.edit()
                        editor.putString("username_login_atual",username)
                        editor.commit()

                        ////////////////////////////

                        Toast.makeText(this@Login, "Login efectuado"+ a.id, Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@Login, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        var token = getSharedPreferences("username", Context.MODE_PRIVATE)
        if(token.getString("username_login_atual"," ") != " ") {

            var tokenid = getSharedPreferences("id", Context.MODE_PRIVATE)
            var editorid = tokenid.edit()
            editorid.putInt("id_login_atual",userid)
            editorid.commit()
            val intent = Intent(this@Login, MapsActivity::class.java)       // ENTRA NA ATIVIDADE

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}