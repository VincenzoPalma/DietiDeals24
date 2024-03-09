package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Carta
import com.example.dietideals_app.model.dto.CreaCarta
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface CartaService {

    @POST("utente/carte")
    fun saveCarta(@Body creaCarta: CreaCarta): Call<Carta>

    @DELETE("carte/{idCarta}")
    fun deleteCarta(@Path("idCarta") idCarta: UUID): Call<Unit>

    @GET("utente/carte")
    fun getCarteUtente(): Call<List<Carta>>
}