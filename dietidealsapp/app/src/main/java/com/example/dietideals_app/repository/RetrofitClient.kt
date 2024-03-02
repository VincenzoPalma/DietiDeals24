package com.example.dietideals_app.repository

import com.example.dietideals_app.repository.interfacceRepository.AstaService
import com.example.dietideals_app.repository.interfacceRepository.UtenteService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private suspend fun getAccessToken(): String? {
        return suspendCoroutine { continuation ->
            val user = Firebase.auth.currentUser
            if (user != null) {
                user.getIdToken(false)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val tokenResult = task.result
                            val token = tokenResult?.token
                            continuation.resume(token)
                        } else {
                            continuation.resume(null)
                        }
                    }
            } else {
                continuation.resume(null)
            }
        }
    }

    fun getClientWithAuthorization(): Retrofit {
        val clientWithAuthorization = OkHttpClient.Builder().addInterceptor { chain ->
            val token = runBlocking { getAccessToken() }
            if (!token.isNullOrEmpty()) {
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            } else {
                println("non autenticato") //gestire errori
                chain.proceed(chain.request())
            }
        }.build()

        return Retrofit.Builder()
            .client(clientWithAuthorization)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapter(
                    OffsetDateTime::class.java,
                    JsonDeserializer { json, type, jsonDeserializationContext ->
                        val text = json.getAsJsonPrimitive().asString
                        OffsetDateTime.parse(text)
                    }).create())).build()
    }

    val retrofitWithoutAuthorization: Retrofit by lazy { //da usare solo per la registrazione (senza autenticazione)
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().registerTypeAdapter(
                    OffsetDateTime::class.java,
                    JsonDeserializer { json, type, jsonDeserializationContext ->
                        val text = json.getAsJsonPrimitive().asString
                        OffsetDateTime.parse(text)
                    }).create())).build()
    }

    object ApiRegistrazione {
        val utenteService: UtenteService by lazy {
            retrofitWithoutAuthorization.create(UtenteService::class.java)
        }

    }

}

    object ApiUtente {
        val utenteService: UtenteService by lazy {
            RetrofitClient.getClientWithAuthorization().create(UtenteService::class.java)
        }

    }

    object ApiAsta {
        val astaService: AstaService by lazy {
            RetrofitClient.getClientWithAuthorization().create(AstaService::class.java)
        }
    }
