package com.risajtr.moviesapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class for [MoviesResponse].
 */
data class MoviesResponse(
    @SerializedName("page")
    val page: Page?
)