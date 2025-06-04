package com.example.pelis
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private val apiKey = "fb7aca4" //4290dbbf

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imagePoster = findViewById<ImageView>(R.id.imagePosterDetail)
        val textTitle = findViewById<TextView>(R.id.textTitleDetail)
        val textYear = findViewById<TextView>(R.id.textYearDetail)
        val textPlot = findViewById<TextView>(R.id.textPlot)
        val textRuntime = findViewById<TextView>(R.id.textRuntime)
        val textDirector = findViewById<TextView>(R.id.textDirector)
        val textGenre = findViewById<TextView>(R.id.textGenre)
        val textCountry = findViewById<TextView>(R.id.textCountry)
        val imdbID = intent.getStringExtra("imdbID") ?: return

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(OmdbApi::class.java)

        api.getMovieDetail(apiKey, imdbID).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                val movie = response.body()
                if (movie != null) {
                    textTitle.text = movie.Title
                    textYear.text = movie.Year
                    Picasso.get().load(movie.Poster).into(imagePoster)
                    textPlot.text = movie.Plot
                    textRuntime.text = "Duración: ${movie.Runtime}"
                    textDirector.text = "Director: ${movie.Director}"
                    textGenre.text = "Género: ${movie.Genre}"
                    textCountry.text = "País: ${movie.Country}"
                } else {
                    Toast.makeText(this@DetailActivity, "No se pudo cargar la película", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
