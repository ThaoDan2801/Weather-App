package com.glucozo.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.glucozo.weather_app.databinding.ActivityMainBinding
import com.glucozo.weather_app.mode.WeatherApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//9396dfe6d7d154a49e529e92d9869dd8
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var location = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        searchLocation()
        fetchWeatherData("Hanoi")
    }
    private fun searchLocation() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                    location = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun setupView() {
        binding.imgHumidity.setImageResource(R.drawable.icon_a)
        binding.imgWindSpeed.setImageResource(R.drawable.icon_wind_speed)
        binding.imgConditions.setImageResource(R.drawable.icon_conditions)
        binding.imgSunrise.setImageResource(R.drawable.icon_sunrise)
        binding.imgSunset.setImageResource(R.drawable.icon_sunset)
        binding.imgSea.setImageResource(R.drawable.icon_sea)
    }

    private fun fetchWeatherData(place: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(WeatherAppInterface::class.java)

        val response =
            retrofit.getWeatherData(place, "9396dfe6d7d154a49e529e92d9869dd8")
        response.enqueue(object : Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val temperature = responseBody.main.temp
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise
                    val sunSet = responseBody.sys.sunset
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unknown"
                    val tempMax = responseBody.main.temp_max
                    val tempMin = responseBody.main.temp_min
                    binding.tvTemperature.text = temperature.toInt().toString()
                    binding.tvTitle.text = condition
                    binding.tvTemperatureMax.text = tempMax.toString()
                    binding.tvTemperatureMin.text = tempMin.toString()
                    binding.tvHumidity.text = humidity.toString()
                    binding.tvWindSpeed.text = windSpeed.toString()
                    binding.tvConditions.text = condition
                    binding.tvSunrise.text = sunRise.toString()
                    binding.tvSunset.text = sunSet.toString()
                    binding.tvSea.text = seaLevel.toString()
                    binding.tvLocation.text = place
                    binding.tvWeekdays.text = dayName(System.currentTimeMillis())
                    binding.tvDate.text = date()
                    changeImage(condition)
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {

            }
        })

    }

    private fun changeImage(condition: String) {
        when(condition){
            "Haze" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
        }
        binding.lottieAnimationView.playAnimation()

    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onResume() {
        super.onResume()

        fetchWeatherData(location)
    }
}