package com.example.surfme.services

import com.example.surfme.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {
    @GET("/3/movie/popular?api_key=8f7e8262951851e9cd40e68b53f7df38")
    fun getMoviesList(): Call<MovieResponse>
}