package com.bignerdranch.android.prakt52

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pr_32_kurs_5_kachalinsemen_6v.R
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    private val API_KEY = "J9PA2FM-SK9M56X-PFNCXV6-AKPZ3JK"
    private val URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films?text="

    private lateinit var recyclerView: RecyclerView
    private val movies = mutableListOf<Movie>()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnSaved = findViewById<Button>(R.id.btnSaved)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(movies) { movie ->
            saveMovie(movie) // Сохраняем в ROOM при клике
        }
        recyclerView.adapter = adapter

        // Кнопка "Найти"
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Введите название!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            searchMovie(query)
        }

        // Кнопка "Избранное"
        btnSaved.setOnClickListener {
            startActivity(Intent(this, SavedActivity::class.java))
        }
    }

    // Функция запроса к API (как в примере с OpenWeatherMap!)
    private fun searchMovie(query: String) {
        val url = URL + query + "&apiKey=" + API_KEY

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                // Логируем ответ (как в Logcat из методички)
                Log.d("API", response)

                // Преобразуем JSON в объект через Gson
                val gson = Gson()
                val movieResponse = gson.fromJson(response, MovieResponse::class.java)

                movies.clear()
                movies.addAll(movieResponse.films)
                adapter.notifyDataSetChanged()

                Toast.makeText(this, "Найдено: ${movies.size}", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Log.e("API", "Ошибка: ${error.message}")
                Toast.makeText(this, "Ошибка сети", Toast.LENGTH_SHORT).show()
            }
        )

        // Добавляем заголовок с ключом API
        stringRequest.headers = mapOf("J9PA2FM-SK9M56X-PFNCXV6-AKPZ3JK" to API_KEY)

        // Выполняем запрос
        Volley.newRequestQueue(this).add(stringRequest)
    }

    // Сохранение фильма в ROOM
    private fun saveMovie(movie: Movie) {
        val entity = MovieEntity(
            id = movie.kinopoiskId,
            name = movie.nameRu ?: "Без названия",
            year = movie.year,
            rating = movie.rating,
            poster = movie.posterUrl
        )

        // ROOM нельзя использовать в главном потоке — запускаем в фоне
        Thread {
            AppDatabase.getDatabase(this).movieDao().insert(entity)
            runOnUiThread {
                Toast.makeText(this, "Сохранено!", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}