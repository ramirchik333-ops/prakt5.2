package com.bignerdranch.android.prakt52
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val movies = mutableListOf<MovieEntity>()
    private lateinit var adapter: SavedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        recyclerView = findViewById(R.id.recyclerViewSaved)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadMovies()
    }

    // Загружаем фильмы из базы
    private fun loadMovies() {
        Thread {
            val list = AppDatabase.getDatabase(this).movieDao().getAll()
            runOnUiThread {
                movies.clear()
                movies.addAll(list)
                adapter = SavedAdapter(movies) { movie ->
                    deleteMovie(movie)
                }
                recyclerView.adapter = adapter

                if (movies.isEmpty()) {
                    Toast.makeText(this, "Список пуст", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    // Удаляем фильм
    private fun deleteMovie(movie: MovieEntity) {
        Thread {
            AppDatabase.getDatabase(this).movieDao().delete(movie)
            runOnUiThread {
                loadMovies() // Перезагружаем список
            }
        }.start()
    }
}