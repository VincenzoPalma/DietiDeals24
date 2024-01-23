package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Utente
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UtenteService {
    @GET("users/{username}")
    suspend fun getUtente(@Path("username") userId: String): Utente

    @GET("users")
    suspend fun getUtenti(): List<Utente>

    @POST("users")
    suspend fun createUtente(@Body user: Utente): Utente

    @PUT("users/{username}")
    suspend fun updateUtente(@Path("username") userId: String, @Body user: Utente): Utente

    @DELETE("users/{username}")
    suspend fun deleteUtenti(@Path("username") userId: String): Response<Void>
}