package com.risajtr.moviesapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for [ContentItems].
 */
data class ContentItems(
    @SerializedName("content")
    val content: List<Movie?>?
)