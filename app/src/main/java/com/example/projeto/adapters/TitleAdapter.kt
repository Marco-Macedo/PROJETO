package com.example.projeto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto.R
import com.example.projeto.entities.Title

class TitleAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var titles = emptyList<Title>() // Cached copy of words

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerline, parent, false)
        return TitleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val current = titles[position]
        holder.titleItemView.text = current.title
    }

    internal fun setTitles(titles: List<Title>) {
        this.titles = titles
        notifyDataSetChanged()
    }

    override fun getItemCount() = titles.size
}