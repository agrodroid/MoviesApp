package com.risajtr.moviesapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for [Movie].
 */
data class Movie(
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster-image")
    val posterImage: String?
)