package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import com.example.dietideals_app.model.enum.RuoloUtente
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
    fun createUtente(
        @Body datiRegistrazione: UtenteRegistrazione,
        @Query("idFirebase") idFirebase: String?
    ): Call<Utente>

    @GET("registrazione/esisteEmail/{email}")
    fun getUtenteByEmail(@Path("email") email: String): Call<Utente>

    @PUT("utente/modificaDatiUtente")
    fun modificaDatiUtente(@Body datiProfiloUtente: DatiProfiloUtente): Call<Utente>

    @PUT("utente/datiVenditore/partitaIva")
    fun modificaPartitaIva(@Body partitaIva: String): Call<Utente>

    @PUT("utente/datiVenditore/documentoVenditore")
    fun modificaDocumentoVenditore(@Body urlDocumento: String): Call<Utente>

    @PUT("utente/ruolo")
    fun setUtenteVenditore(): Call<Utente>

    @GET("utente/datiUtente")
    fun getDatiUtente(@Query("idUtente") idUtente: UUID?): Call<DatiProfiloUtente>

    @GET("/utente/ruolo")
    fun getRuoloUtente(): Call<RuoloUtente>

    @GET("utente/idUtente/{idAuth}")
    fun getUtenteByIdAuth(@Path("idAuth") idAuth: String): Call<Utente>

    @GET("utente/partitaIva")
    fun getPartitaIvaUtente(): Call<String>

}