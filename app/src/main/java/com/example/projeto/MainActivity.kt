package com.example.projeto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

    fun insert(view: View) {
        myList.add(0, Place("Title XXX","DATA XXX", "Notes XXX"))
        recycler_view.adapter?.notifyDataSetChanged()
    }
}