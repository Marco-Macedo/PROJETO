package com.example.projeto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapters.TitleAdapter
import com.example.projeto.viewModel.TitleViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var titleViewModel: TitleViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    // recycler view
        val recyclerView = findViewById<RecyclerView.Recycler>(R.id.recyclerview)
        val adapter = TitleAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
            R.id.remove -> {
                Toast.makeText(this, "@string/Remove", Toast.LENGTH_SHORT ).show()
                val intent = Intent(this, Remover::class.java).apply {}
                startActivity(intent)
                true
            }
            R.id.edit -> {
                Toast.makeText(this, "@string/Edit", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Editar::class.java).apply {}
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}