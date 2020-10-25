package com.example.projeto.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.projeto.R
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.dataclasses.Place
import kotlinx.android.synthetic.main.recyclerline.view.*

class LineAdapter(val list: ArrayList<Place>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        holder.name.text = currentPlace.title
        holder.capital.text = currentPlace.notes
        holder.nhabitants.text = currentPlace.date.toString()
    }

}

class LineViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView){
    val name = itemView.title
    val capital = itemView.notes
    var nhabitants = itemView.date
}