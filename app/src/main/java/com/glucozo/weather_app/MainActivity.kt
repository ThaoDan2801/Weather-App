package com.glucozo.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glucozo.weather_app.databinding.ActivityMainBinding
import com.glucozo.weather_app.databinding.ActivitySignInBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}