package com.risajtr.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.risajtr.moviesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * The activity which holds the two fragments.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}