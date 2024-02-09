package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Asta
import com.example.dietideals_app.model.enum.CategoriaAsta
import com.example.dietideals_app.model.enum.TipoAsta
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("aste")
    suspend fun getAste(@Query("page") page : Int, @Query("size") size : Int = 12, @Query("nome") nomeRicerca: String? = null, @Query("categoria") categoria: CategoriaAsta? = null, @Query("tipo") tipo: TipoAsta? = null) : List<Asta>
}