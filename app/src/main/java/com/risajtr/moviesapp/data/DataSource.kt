package com.risajtr.moviesapp.data

import android.content.Context
import com.risajtr.moviesapp.data.model.MoviesResponse
import com.risajtr.moviesapp.utils.JsonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This class acts as central point for data, by fetching, parsing and returning data in required format.
 * @param context [Context].
 */
class DataSource(private val context: Context) {

    /**
     * Returns a [MoviesResponse] object when given a page number. Takes care of all fetching and parsing.
     * @param pageNumber [Int].
     */
    suspend fun fetchMovies(pageNumber: Int): MoviesResponse? {
        return try {
            val jsonString =
                JsonUtils.getJsonFromAsset(
                    context,
                    "CONTENTLISTINGPAGE-PAGE${pageNumber}.json"
                )

            // Parses the JSON in the background
            val parsedJson = withContext(Dispatchers.IO) { JsonUtils.parseJson(jsonString) }
            parsedJson
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}