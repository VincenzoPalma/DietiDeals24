package com.example.dietideals_app.repository

import com.example.dietideals_app.repository.interfacceRepository.AstaService
import com.example.dietideals_app.repository.interfacceRepository.UtenteService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val clientWithAuthorization: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer token")//capire come passare il token dell'utente loggato
            .build()
        chain.proceed(newRequest)
    }.build()

    val retrofitWithAuthorization: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientWithAuthorization)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitWithoutAuthorization: Retrofit by lazy { //da usare solo per la registrazione (senza autenticazione)
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiUtente {
    val utenteService: UtenteService by lazy {
        RetrofitClient.retrofitWithAuthorization.create(UtenteService::class.java)
    }

}

object ApiAsta {
    val astaService: AstaService by lazy {
        RetrofitClient.retrofitWithAuthorization.create(AstaService::class.java)
    }
}