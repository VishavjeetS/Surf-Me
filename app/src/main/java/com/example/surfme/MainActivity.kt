package com.example.surfme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surfme.adapter.MovieAdapter
import com.example.surfme.model.MovieResponse
import com.example.surfme.model.Movies
import com.example.surfme.services.MovieApiInterface
import com.example.surfme.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movie_recycler.layoutManager = LinearLayoutManager(this)
        movie_recycler.setHasFixedSize(true)
        getMovieData { movies: List<Movies> ->
            Collections.sort(movies,
                Comparator<Movies> { lhs, rhs -> (rhs.vote_count!!.toInt()).compareTo(lhs.vote_count!!.toInt()) })
            val adapter = MovieAdapter(movies)
            movie_recycler.adapter = adapter
            adapter.notifyDataSetChanged()
            adapter.setOnItemClickListener(object : MovieAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(applicationContext, detailActivity::class.java)
                    val image = IMAGE_BASE + movies[position].poster_path.toString()
                    Log.d("ImageUri", image)
                    intent.putExtra("image", image)
                    intent.putExtra("title", movies[position].title.toString())
                    intent.putExtra("overview", movies[position].overview.toString())
                    startActivity(intent)
                }
            })
        }
    }

    private fun getMovieData(callback: (List<Movies>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMoviesList().enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            }

        })
    }
}