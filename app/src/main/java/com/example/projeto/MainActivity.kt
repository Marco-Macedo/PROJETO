package com.example.projeto

import android.app.Activity
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
import com.example.projeto.entities.Title
import com.example.projeto.viewModel.TitleViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var titleViewModel: TitleViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TitleAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // view model
        titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        titleViewModel.allTitles.observe(this, { titles ->
            // Update the cached copy of the words in the adapter.
            titles?.let { adapter.setTitles(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent( this@MainActivity, AddTitle::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(AddTitle.EXTRA_REPLY)?.let {
                val title = Title(title = it, notes = "Gym", date = "27/10/2020")
                titleViewModel.insert(title)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Titulo vazio: não inserido",
                Toast.LENGTH_LONG).show()

        }
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