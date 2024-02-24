package com.example.dietideals_app.repository

import com.example.dietideals_app.repository.interfacceRepository.AstaService
import com.example.dietideals_app.repository.interfacceRepository.UtenteService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val clientWithAuthorization: OkHttpClient =
        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImExODE4ZjQ0ODk0MjI1ZjQ2MWQyMmI1NjA4NDcyMDM3MTc2MGY1OWIiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiUGllcm8iLCJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vZGlldGlkZWFsczI0LWRlNjdkIiwiYXVkIjoiZGlldGlkZWFsczI0LWRlNjdkIiwiYXV0aF90aW1lIjoxNzA4Nzc4NTUxLCJ1c2VyX2lkIjoieGpHa2lCeHpaM2ZMZ2V0T2hCeW5qdWN6RmZEMyIsInN1YiI6InhqR2tpQnh6WjNmTGdldE9oQnluanVjekZmRDMiLCJpYXQiOjE3MDg3Nzg1NTEsImV4cCI6MTcwODc4MjE1MSwiZW1haWwiOiJwaWVyb0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicGllcm9AZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.jSL6Unc6CL4EWNqKoAO1aafc46bxQGST4bT2Pv7zHrxghR59DYV6oY9hLqN_KUsn-4TNTnf2MY4UJgRk7VK35vcPNJdtgkgiOASRI46q2bxQ-CMQnw7GRAZ-bR9fVGOAiPpQJlVnLYgui5CdlphC-9C3KFjnp65I4Cf3Wq3ClFcIZ-nQl2tQ3d3dhghY6UjdSQnpTTLv_5ayPFak_wjDXGBECT3FvLVIKUpJ3YjJpMqJ_N7f9lUezbXCb5yqe99YHRUZdENS4wgYlbEC9d9MTRzqPNPsiv07XgIt0S1xOm68pixDlqD6BD9gL545lMfMoJGqtqb5hsT8Ss0plk2wlw"
                ) //capire come passare il token dell'utente loggato
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

    /*   val retrofitLogin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://identitytoolkit.googleapis.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiLogin {
    val utenteService: UtenteService by lazy {
        RetrofitClient.retrofitLogin.create(UtenteService::class.java)
    }*/
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
