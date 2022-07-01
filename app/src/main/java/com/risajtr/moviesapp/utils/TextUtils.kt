package com.risajtr.moviesapp.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan

/**
 * Utilities class for functions related to text.
 */
class TextUtils {

    companion object {
        /**
         * Returns a spannable string with the given search query part in yellow colour.
         * In case of errors, the input name is returned as a spannable string.
         * @param name The movie name.
         * @param searchQuery Search query.
         */
        fun getColouredName(name: String, searchQuery: String): SpannableString {
            return try {
                val startIndex = name.indexOf(searchQuery, ignoreCase = true)
                val endIndex = startIndex + searchQuery.length
                val colouredName = SpannableString(name)
                colouredName.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    startIndex,
                    endIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                colouredName
            } catch (e: Exception) {
                e.stackTraceToString()
                SpannableString(name)
            }
        }
    }

}