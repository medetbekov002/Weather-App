package com.example.weatherapp.network.apiimport com.example.weatherapp.data.model.RemoteLocationimport retrofit2.Responseimport retrofit2.http.GETimport retrofit2.http.Queryinterface WeatherApi {    companion object {        const val BASE_URL = "https://api.weatherapi.com/v1/"        const val API_KEY = "7acd3ce917d142d58f662110242904"    }    @GET("search.json")    suspend fun searchLocation(        @Query("key") key: String = API_KEY,        @Query("q") query: String    ): Response<List<RemoteLocation>>}