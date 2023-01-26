package com.Tejas.firstapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.Tejas.firstapp.api.CurrentWeather
import com.Tejas.firstapp.api.WeeklyForecast
import com.Tejas.firstapp.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random




class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather :LiveData<CurrentWeather> = _currentWeather

    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast:LiveData<WeeklyForecast> = _weeklyForecast

    fun loadCurrentForecast(zipcode: String){

        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.api_key)

        call.enqueue(object :Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    _currentWeather.value = weatherResponse!!
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName ,"Error Loading Current Forecast",t)
            }
        }
        )
    }

    fun loadWeeklyForecast(zipcode : String){
        val call = createOpenWeatherMapService().currentWeather(zipcode,"imperial",BuildConfig.api_key)

        call.enqueue(object :Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if(weatherResponse != null){
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lon = weatherResponse.coord.lon,
                        lat = weatherResponse.coord.lat,
                        exclude = "current,minutely,hourly",
                        units = "imperial",
                        apikey = BuildConfig.api_key

                    )
                    forecastCall.enqueue(object : Callback<WeeklyForecast>{
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse != null){
                                _weeklyForecast.value = weeklyForecastResponse!!
                            }
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName,  "error loading data on weekly forecast")
                        }

                    })
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName ,"Error Loading Current Location For Weekly Forecast",t)
            }
        }
        )
    }

//    private fun getTempDescription(temperature : Float) : String{
//        return when(temperature){
//            in Float.MIN_VALUE.rangeTo(0f)->"Impossible to stay"
//            in 0f.rangeTo(32f)->"Freezer"
//            in 32f.rangeTo(48f)->"Still cold"
//            in 48f.rangeTo(64f)->"Getting better"
//            in 64f.rangeTo(80f)->"Perfect for sleeping"
//            in 80f.rangeTo(100f)->"Turn On the AC"
//            else -> "I missed the temperature "
//        }
//    }
}