package com.bignerdranch.android.prakt52

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(
    private val movies: List<Movie>,
    private val onSaveClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvYear: TextView = view.findViewById(R.id.tvYear)
        val tvRating: TextView = view.findViewById(R.id.tvRating)
        val btnSave: Button = view.findViewById(R.id.btnSave)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvName.text = movie.nameRu ?: "Нет названия"
        holder.tvYear.text = "Год: ${movie.year ?: "неизвестно"}"
        holder.tvRating.text = "Рейтинг: ${movie.rating ?: "—"}"

        holder.btnSave.setOnClickListener {
            onSaveClick(movie)
        }
    }

    override fun getItemCount() = movies.size
}