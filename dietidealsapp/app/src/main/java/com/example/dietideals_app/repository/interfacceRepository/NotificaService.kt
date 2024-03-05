package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Notifica
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET

interface NotificaService {

    @GET("utente/notifiche")
    fun getNotificheUtente(): Call<List<Notifica>>

    @DELETE("utente/notifiche")
    fun deleteNotifiche(): Call<Unit>
}