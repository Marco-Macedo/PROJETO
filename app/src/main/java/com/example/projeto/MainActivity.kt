package com.example.projeto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.adapters.TitleAdapter
import com.example.projeto.entities.Title
import com.example.projeto.viewModel.TitleViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {



    private lateinit var titleViewModel: TitleViewModel
    private val newWordActivityRequestCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TitleAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        titleViewModel=ViewModelProvider(this).get(TitleViewModel::class.java)
        titleViewModel.allTitles.observe(this, Observer { titles ->
            // Update the cached copy of the words in the adapter.
            titles?.let { adapter.setTitles(it) }
        })

        //VIEW MODEL

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddTitle::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val ptitle = data?.getStringExtra(AddTitle.EXTRA_REPLY_TITLE)
            val pnote = data?.getStringExtra(AddTitle.EXTRA_REPLY_NOTES)
            val pdate = data?.getStringExtra(AddTitle.EXTRA_REPLY_DATE)


            if (ptitle != null && pnote != null && pdate != null) {
                val note = Title(title = ptitle, notes = pnote, date = pdate)
                titleViewModel.insert(note)
            }
        }
        else {
            Toast.makeText(
                    applicationContext,
                    "Titulo vazio!",
                    Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when(item.itemId) {

            R.id.remove_all -> {
                Toast.makeText(this, "Notas Limpas...", Toast.LENGTH_SHORT ).show()
                titleViewModel.deleteAll()
                true
            }
            R.id.View_Monday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Segunda-Feira").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Thursday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Terça-Feira").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Wednesday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Quarta-Feira").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Tuesday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Quinta-Feira").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Friday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Sexta-Feira").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Saturday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Sábado").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.View_Sunday -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.getNotesByTitle("Domingo").observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            R.id.Remove_Monday -> {
                titleViewModel.deleteByTitle("Segunda-Feira")
                true
            }
            R.id.Remove_Thursday -> {
                titleViewModel.deleteByTitle("Terça-Feira")
                true
            }
            R.id.Remove_Wednesday -> {
                titleViewModel.deleteByTitle("Quarta-Feira")
                true
            }
            R.id.Remove_Tuesday -> {
                titleViewModel.deleteByTitle("Quinta-Feira")
                true
            }
            R.id.Remove_Friday -> {
                titleViewModel.deleteByTitle("Sexta-Feira")
                true
            }
            R.id.Remove_Saturday -> {
                titleViewModel.deleteByTitle("Sábado")
                true
            }
            R.id.Remove_Sunday -> {
                titleViewModel.deleteByTitle("Domingo")
                true
            }
            R.id.View_All -> {
                // recycler view
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = TitleAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)

                // view model
                titleViewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
                titleViewModel.allTitles.observe(this, Observer { titles ->
                    // Update the cached copy of the words in the adapter.
                    titles?.let { adapter.setTitles(it) }
                })

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}