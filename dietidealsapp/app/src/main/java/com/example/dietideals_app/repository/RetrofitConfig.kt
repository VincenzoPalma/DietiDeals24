package com.example.dietideals_app.repository

import com.example.dietideals_app.repository.interfacceRepository.UtenteService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private const val BASE_URL_UTENTE = "https://api.example.com/"

    val utenteService: UtenteService by lazy {
        createService(BASE_URL_UTENTE, UtenteService::class.java)
    }
    private fun <T> createService(baseUrl: String, serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}