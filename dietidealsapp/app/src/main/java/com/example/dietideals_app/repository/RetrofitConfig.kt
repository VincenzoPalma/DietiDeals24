package com.example.dietideals_app.repository

import com.example.dietideals_app.repository.interfacceRepository.ApiService
import com.example.dietideals_app.repository.interfacceRepository.UtenteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitConfig {
    private const val BASE_URL = "https://192.168.1.16:8080/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}