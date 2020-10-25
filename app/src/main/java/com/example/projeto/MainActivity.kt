package com.example.projeto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.projeto.dataclasses.Place
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto.adapter.LineAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var myList: ArrayList<Place>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myList = ArrayList<Place>()


        for (i in 0 until 500) {
            myList.add(Place( "Country $i", "20/10/2020 $i", "Capital $i"))
        }
        recycler_view.adapter = LineAdapter(myList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.setHasFixedSize(true)


    }

    /*fun inserir(view: View){
        val intent = Intent(this, Inserir::class.java).apply {
        }
        startActivity(intent)
    }*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when(item.itemId) {
            R.id.insert -> {
                /*Toast.makeText(this, "Inserir", Toast.LENGTH_SHORT).show()*/
                val intent = Intent(this, Inserir::class.java).apply {}
                startActivity(intent)
                true
            }
            R.id.remove -> {
                /*Toast.makeText(this, "Remover", Toast.LENGTH_SHORT ).show()*/
                val intent = Intent(this, Remover::class.java).apply {}
                startActivity(intent)
                true
            }
            R.id.edit -> {
                Toast.makeText(this, "Editar", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Editar::class.java).apply {}
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}