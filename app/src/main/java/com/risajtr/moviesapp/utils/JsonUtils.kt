package com.risajtr.moviesapp.utils

import android.content.Context
import com.google.gson.Gson
import com.risajtr.moviesapp.data.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Utilities class for functions related to JSON.
 */
class JsonUtils {

    companion object {
        /**
         * When given a valid filename of a JSON file stored in the assets folder, this function returns it as a [String].
         * Otherwise, null is returned.
         * @param context The context.
         * @param fileName The name of file.
         **/
        fun getJsonFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }

        /**
         * When given a valid JSON string, this function parses it and returns a Kotlin [Object].
         * It operates asynchronously.
         * Otherwise, null is returned.
         * @param jsonString The JSON string.
         **/
        suspend fun parseJson(jsonString: String?): MoviesResponse? {
            val jsonResponse: MoviesResponse
            val gson = Gson()
            try {
                jsonResponse =
                    withContext(Dispatchers.IO) {
                        gson.fromJson(
                            jsonString,
                            MoviesResponse::class.java
                        )
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return jsonResponse
        }
    }

}