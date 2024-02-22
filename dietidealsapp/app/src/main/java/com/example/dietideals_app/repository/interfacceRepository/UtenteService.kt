package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface UtenteService {

    @GET("utente/datiUtente")
    fun getDatiUtente(@Query("idUtente") idUtente: UUID?): Call<DatiProfiloUtente>

    @POST("registrazione")
    fun createUtente(@Body datiRegistrazione: UtenteRegistrazione): Utente
}