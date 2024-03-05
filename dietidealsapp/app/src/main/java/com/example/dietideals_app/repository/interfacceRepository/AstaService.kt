package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.dto.CreaAsta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.StatoAsta
import com.example.dietideals_app.model.enum.TipoAsta
import com.example.dietideals_app.model.list.AstaList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AstaService {

    @POST("utente/aste")
    fun saveAsta(@Body creaAsta: CreaAsta): Call<Asta>

    @GET("aste")
    fun getAste(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("nome") nome: String?,
        @Query("tipo") tipo: TipoAsta?,
        @Query("categoria") categoria: CategoriaAsta?
    ): Call<AstaList>

    @GET("utente/aste")
    fun getAsteUtente(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("stato") statoAsta: StatoAsta?
    ): Call<List<Asta>>

    @GET("utente/offerte/asta")
    fun getAstePartecipate(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("vinta") vinta: Boolean?
    ): Call<List<Asta>>
}