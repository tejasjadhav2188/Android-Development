package com.Tejas.firstapp.api

import com.squareup.moshi.Json

data class Forecast(val temp:Float)
data class WeatherIcon (val icon : String)
data class Coordinates(val lat : Float,val lon : Float)

data class CurrentWeather(
    val name:String,
    val coord : Coordinates,
    @field:Json(name="weather") val weatherIcon:List<WeatherIcon>,
    @field:Json(name="main") val forecast: Forecast
)