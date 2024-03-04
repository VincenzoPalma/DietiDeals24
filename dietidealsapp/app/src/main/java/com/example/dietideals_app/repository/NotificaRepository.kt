package com.example.dietideals_app.repository

import com.example.dietideals_app.model.Notifica
import com.example.dietideals_app.model.list.NotificaList
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificaRepository {

    suspend fun getNotifiche(): List<Notifica>? {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<List<Notifica>?>()

            ApiNotifica.notificaService.getNotificheUtente().enqueue(object :
                Callback<List<Notifica>> {
                override fun onResponse(
                    call: Call<List<Notifica>>,
                    response: Response<List<Notifica>>
                ) {
                    if (response.isSuccessful) {
                        val risultato : List<Notifica>? = response.body()
                        deferred.complete(risultato)
                    } else {
                        deferred.complete(null)
                    }
                }

                override fun onFailure(call: Call<List<Notifica>>, t: Throwable) {
                    deferred.complete(null)
                }
            })

            deferred.await()
        }
    }

    suspend fun deleteNotifiche(): Boolean {
        return withContext(Dispatchers.IO) {
            val deferred = CompletableDeferred<Boolean>()

            ApiNotifica.notificaService.deleteNotifiche().enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        deferred.complete(true)
                    } else {
                        deferred.complete(false)
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    deferred.complete(false)
                }
            })

            deferred.await()
        }
    }

}