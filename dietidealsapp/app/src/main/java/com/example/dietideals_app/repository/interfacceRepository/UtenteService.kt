package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface UtenteService {

    @POST("registrazione")
    fun createUtente(@Body datiRegistrazione: UtenteRegistrazione): Call<Utente>

    @GET("registrazione/esisteEmail/{email}")
    fun getUtenteByEmail(@Path("email") email : String): Call<Utente>

    @GET("registrazione/esisteUsername/{username}")
    fun getUtenteByUsername(@Path("username") username : String): Call<Utente>

    @PUT("utente/modificaDatiUtente")
    fun modificaDatiUtente(@Body datiProfiloUtente: DatiProfiloUtente): Call<Utente>

    @GET("utente/datiUtente")
    fun getDatiUtente(@Query("idUtente") idUtente: UUID?): Call<DatiProfiloUtente>

}