package com.example.surfme.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movies>
):Parcelable{
    constructor() : this(mutableListOf())
}