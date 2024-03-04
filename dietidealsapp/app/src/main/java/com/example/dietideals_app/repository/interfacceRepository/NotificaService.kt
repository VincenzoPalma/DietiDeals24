package com.example.dietideals_app.repository.interfacceRepository

import com.example.dietideals_app.model.Notifica
import com.example.dietideals_app.model.list.NotificaList
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface NotificaService {

    @GET("utente/notifiche")
    fun getNotificheUtente() : Call<List<Notifica>>

    @DELETE("utente/notifiche")
    fun deleteNotifiche() : Call<Unit>
}