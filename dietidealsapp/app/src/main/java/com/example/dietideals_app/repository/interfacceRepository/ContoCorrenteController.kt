package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.ContoCorrente
import com.example.dietideals_app.model.dto.CreaContoCorrente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ContoCorrenteController {

    @POST("utente/contoCorrente")
    fun saveContoCorrente(@Body creaContoCorrente: CreaContoCorrente) : Call<ContoCorrente>

    @PUT("contoCorrente")
    fun modifyContoCorrente(@Body contoCorrente: ContoCorrente) : Call<ContoCorrente>

    @GET("utente/contoCorrente")
    fun getContoCorrente() : Call<ContoCorrente>
}