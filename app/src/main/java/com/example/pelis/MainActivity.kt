package com.example.pelis
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieAdapter
    private val movies = mutableListOf<Movie>()
    private val apiKey = "fb7aca4" //4290dbbf
    private lateinit var omdbApi: OmdbApi

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextSearch = findViewById<EditText>(R.id.editTextSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)


        movieAdapter = MovieAdapter(movies) { movie ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("imdbID", movie.imdbID)
            startActivity(intent)
        }
        recyclerView.adapter = movieAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")//?i=tt3896198&apikey=4290dbbf
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        omdbApi = retrofit.create(OmdbApi::class.java)

        btnSearch.setOnClickListener {
            val query = editTextSearch.text.toString()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Toast.makeText(this, "Escribe un nombre de película", Toast.LENGTH_SHORT).show()
            }
        }
    }

  /*  private fun searchMovies(query: String) {
        omdbApi.searchMovies(apiKey, query).enqueue(object : Callback<MovieSearchResult> {
            override fun onResponse(call: Call<MovieSearchResult>, response: Response<MovieSearchResult>) {
                if (response.isSuccessful && response.body()?.Response == "True") {
                    val searchResult = response.body()
                    movies.clear()
                    movies.addAll((searchResult?.search ?: emptyList()) as Collection<Movie>)
                    movieAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieSearchResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }*/
    private fun searchMovies(query: String) {
        omdbApi.searchMovies(apiKey, query).enqueue(object : Callback<MovieSearchResult> {
            override fun onResponse(call: Call<MovieSearchResult>, response: Response<MovieSearchResult>) {
                if (response.isSuccessful && response.body()?.Response == "True") {
                    val searchResult = response.body()
                    Log.d("API_RESPONSE", "Películas recibidas: ${searchResult?.search?.size}")
                    movies.clear()
                    movies.addAll(searchResult?.search ?: emptyList())
                    movieAdapter.notifyDataSetChanged()
                } else {
                    Log.e("API_ERROR", "Respuesta no válida: ${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieSearchResult>, t: Throwable) {
                Log.e("API_FAILURE", "Error de red: ${t.message}", t)
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}


