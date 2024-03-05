package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Offerta
import com.example.dietideals_app.model.enum.StatoOfferta
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal
import java.util.UUID

interface OffertaService {

    @POST("aste/{idAsta}/offerte")
    fun saveOfferta(@Path("idAsta") idAsta: UUID, @Body prezzo: BigDecimal): Call<Offerta>

    @GET("aste/{idAsta}/offerte")
    fun getOfferte(
        @Path("idAsta") idAsta: UUID,
        @Query("statoOfferta") statoOfferta: StatoOfferta
    ): Call<List<Offerta>>

}