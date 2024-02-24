package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Utente
import com.example.dietideals_app.model.authentication.AuthResponse
import com.example.dietideals_app.model.dto.DatiProfiloUtente
import com.example.dietideals_app.model.authentication.LoginInfo
import com.example.dietideals_app.model.dto.UtenteRegistrazione
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.UUID

interface UtenteService {

    @POST("registrazione")
    fun createUtente(@Body datiRegistrazione: UtenteRegistrazione): Call<Utente>

    @PUT("utente/modificaDatiUtente")
    fun modificaDatiUtente(@Body datiProfiloUtente: DatiProfiloUtente): Call<Utente>

    @GET("utente/datiUtente")
    fun getDatiUtente(@Query("idUtente") idUtente: UUID?): Call<DatiProfiloUtente>

    /*@POST
    fun loginUtente(@Url url: String, @Query("key") apiKey : String, @Body datiLogin : LoginInfo): Call<AuthResponse>*/


}