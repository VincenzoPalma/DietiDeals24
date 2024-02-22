package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AstaService {

    @GET("aste")
    fun getAste(@Query("page") page: Int, @Query("size") size: Int, @Query("nome") nome: String?, @Query("tipo") tipo: TipoAsta?, @Query("categoria") categoria: CategoriaAsta?): Call<List<Asta>>
}